package io.github.horaciocome1.nexthome.ui.ad

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import io.github.horaciocome1.nexthome.R
import io.github.horaciocome1.nexthome.databinding.FragmentADBinding

/**
 * A simple [Fragment] subclass.
 */
class ADFragment : Fragment() {

    private val viewModel: ADViewModel by lazy {
        ViewModelProvider(this)[ADViewModel::class.java]
    }

    private lateinit var binding: FragmentADBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentADBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        arguments?.let {
            val adId = ADFragmentArgs.fromBundle(it).adId
            setDataToUI(adId)
        }
    }

    private fun setDataToUI(adId: String) = lifecycleScope.launchWhenStarted {
        val ad = viewModel.retrieveAD(adId)
    }

}
