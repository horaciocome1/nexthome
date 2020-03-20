package io.github.horaciocome1.nexthome.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
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

class RentingFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val viewModel: ADsViewModel by lazy {
        ViewModelProvider(this)[ADsViewModel::class.java]
    }

    private lateinit var binding: ListBinding

    private val aDsAdapter: ADsAdapter by lazy {
        ADsAdapter { view, adId -> viewModel.navigateToAD(view, adId) }
    }

    private val snackbar: Snackbar by lazy {
        Snackbar.make(binding.root, "", Snackbar.LENGTH_INDEFINITE)
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
        initZonasSpinner()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) { }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (view is TextView) {
            viewModel.selectedZona = view.text.toString()
            setDataToAdapter()
        }
    }

    private fun setDataToAdapter() = lifecycleScope.launchWhenStarted {
        snackbar.setText("Por favor espere . . .")
            .show()
        val ads = viewModel.retrieveRentingADs()
        aDsAdapter.ads = ads
        if (ads.isEmpty()) {
            val message = "Não foi possível encontrar anúncios. Tente novamente mais tarde"
            snackbar.setText(message)
        } else snackbar.dismiss()
    }

    private fun initUI() {
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(SPAN_COUNT, VERTICAL)
        binding.recyclerView.adapter = aDsAdapter
        initZonasSpinner()
    }

    private fun initZonasSpinner() = lifecycleScope.launchWhenStarted {
        val zonas = viewModel.retrieveZonas()
        context?.let {
            val adapter = ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, zonas)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.zonasSpinner.adapter = adapter
        }
        binding.zonasSpinner.onItemSelectedListener = this@RentingFragment
    }

    companion object {

        const val SPAN_COUNT = 2

    }

}