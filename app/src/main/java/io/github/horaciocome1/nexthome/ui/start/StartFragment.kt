package io.github.horaciocome1.nexthome.ui.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import io.github.horaciocome1.nexthome.R
import io.github.horaciocome1.nexthome.databinding.FragmentStartBinding

/**
 * Fragment responsible for manage the start screen
 */
class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding

    private val viewModel: ADsViewModel by lazy {
        ViewModelProvider(this)[ADsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initTabs()
    }

    private fun initTabs() {
        val adapter = ADsPagerAdapter(
            fragmentManager = childFragmentManager,
            pageTitles = arrayListOf(
                getText(R.string.renting),
                getText(R.string.selling),
                getText(R.string.saved)
            )
        )
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

}
