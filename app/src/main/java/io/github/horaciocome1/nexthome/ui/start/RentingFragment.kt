package io.github.horaciocome1.nexthome.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.github.horaciocome1.nexthome.R
import io.github.horaciocome1.nexthome.databinding.ListBinding
import io.github.horaciocome1.nexthome.util.displaySnackbar
import kotlinx.android.synthetic.main.layout_list_filter.view.*

class RentingFragment : Fragment() {

    private val viewModel: ADsViewModel by lazy {
        ViewModelProvider(this)[ADsViewModel::class.java]
    }

    private lateinit var binding: ListBinding

    private val aDsAdapter: ADsAdapter by lazy {
        ADsAdapter { view, adId -> viewModel.navigateToAD(view, adId) }
    }

    private val roomsSpinnerOnItemSelectedListener = object: AdapterView.OnItemSelectedListener {

        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            viewModel.filter.rooms = position + 1
        }

    }

    private val wcsSpinnerOnItemSelectedListener = object: AdapterView.OnItemSelectedListener {

        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            viewModel.filter.wcs = position + 1
        }

    }

    private val orderBySpinnerOnItemSelectedListener = object: AdapterView.OnItemSelectedListener {

        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            viewModel.filter.orderBy = position
        }

    }

    private val hoodsSpinnerOnItemSelectedListener = object: AdapterView.OnItemSelectedListener {

        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (view is TextView)
                viewModel.filter.hood = view.text.toString()
        }

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
    }

    private fun initUI() {
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(SPAN_COUNT, VERTICAL)
        binding.recyclerView.adapter = aDsAdapter
        binding.includeFilter.goButton.setOnClickListener(this::onGoButtonClicked)
        initRoomsSpinner()
        initWCsSpinner()
        initHoodsSpinner()
        initOrderBySpinner()
        binding.includeFilter.waterCheckBox.setOnClickListener {
            viewModel.filter.hasWater = (it as CheckBox).isChecked
        }
        binding.includeFilter.lightCheckBox.setOnClickListener {
            viewModel.filter.hasLight = (it as CheckBox).isChecked
        }
        binding.includeFilter.furnitureCheckBox.setOnClickListener {
            viewModel.filter.hasFurniture = (it as CheckBox).isChecked
        }
    }

    private fun onGoButtonClicked(view: View) {
        view.isEnabled = false
        displaySnackbar(message = "Processando . . .")
        setDataToAdapter()
    }

    private fun setDataToAdapter() = lifecycleScope.launchWhenStarted {
        aDsAdapter.ads = listOf()
        val ads = viewModel.retrieveRentingADs()
        aDsAdapter.ads = ads
        binding.includeFilter.goButton.isEnabled = true
    }

    private fun initRoomsSpinner() = ArrayAdapter.createFromResource(
        context!!,
        R.array.rooms,
        R.layout.layout_spinner_item
    ).also { adapter ->
        adapter.setDropDownViewResource(R.layout.layout_spinner_dropdown_item)
        binding.includeFilter.roomsSpinner.adapter = adapter
        binding.includeFilter.roomsSpinner.onItemSelectedListener =
            roomsSpinnerOnItemSelectedListener
    }

    private fun initWCsSpinner() = ArrayAdapter.createFromResource(
        context!!,
        R.array.wcs,
        R.layout.layout_spinner_item
    ).also { adapter ->
        adapter.setDropDownViewResource(R.layout.layout_spinner_dropdown_item)
        binding.includeFilter.wcsSpinner.adapter = adapter
        binding.includeFilter.wcsSpinner.onItemSelectedListener =
            wcsSpinnerOnItemSelectedListener
    }

    private fun initOrderBySpinner() = ArrayAdapter.createFromResource(
        context!!,
        R.array.orderBy,
        R.layout.layout_spinner_item
    ).also { adapter ->
        adapter.setDropDownViewResource(R.layout.layout_spinner_dropdown_item)
        binding.includeFilter.orderBySpinner.adapter = adapter
        binding.includeFilter.orderBySpinner.onItemSelectedListener =
            orderBySpinnerOnItemSelectedListener
    }

    private fun initHoodsSpinner() = lifecycleScope.launchWhenStarted {
        val hoods = viewModel.retrieveHoods()
        context?.let {
            val adapter = ArrayAdapter(it, R.layout.layout_spinner_item, hoods)
            adapter.setDropDownViewResource(R.layout.layout_spinner_dropdown_item)
            binding.includeFilter.hoodsSpinner.adapter = adapter
        }
        binding.includeFilter.hoodsSpinner.onItemSelectedListener =
            hoodsSpinnerOnItemSelectedListener
    }

    companion object {

        const val SPAN_COUNT = 2

    }

}