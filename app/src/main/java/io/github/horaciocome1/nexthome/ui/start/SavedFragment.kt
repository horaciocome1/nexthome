package io.github.horaciocome1.nexthome.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.github.horaciocome1.nexthome.databinding.ListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

class SavedFragment : Fragment() {

    private val viewModel: ADsViewModel by lazy {
        ViewModelProvider(this)[ADsViewModel::class.java]
    }

    private lateinit var binding: ListBinding

    private val aDsAdapter: ADsAdapter by lazy {
        ADsAdapter { view, adId -> viewModel.navigateToAD(view, adId) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        setDataToAdapter()
    }

    private fun setDataToAdapter() = lifecycleScope.launchWhenStarted {
        val ads = viewModel.retrieveSavedADs()
        aDsAdapter.ads = ads
    }

    private fun initUI() {
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(SPAN_COUNT, VERTICAL)
        binding.recyclerView.adapter = aDsAdapter
        binding.includeFilter.visibility = View.GONE
    }

    companion object {

        const val SPAN_COUNT = 2

    }

}