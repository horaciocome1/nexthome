package io.github.horaciocome1.nexthome.ui.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import io.github.horaciocome1.nexthome.R
import io.github.horaciocome1.nexthome.data.ad.ADsService
import io.github.horaciocome1.nexthome.databinding.FragmentCreateADBinding
import io.github.horaciocome1.nexthome.util.displaySnackbar

/**
 * Fragment responsible for handling the creation of ADs
 */
class CreateADFragment : Fragment(), OnSeekBarChangeListener, View.OnClickListener,
    AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentCreateADBinding

    private val viewModel: CreateADViewModel by lazy {
        ViewModelProvider(this)[CreateADViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateADBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        when (seekBar.id) {
            R.id.roomsSeekBar -> setQuartosSeekBarProgress(progress)
            R.id.wcsSeekBar -> setWCsSeekBarProgress(progress)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) { }

    override fun onStopTrackingTouch(seekBar: SeekBar) { }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.publicarMaterialButton -> onPublishingButtonClicked()
            R.id.rentingRadioButton -> if (view is RadioButton) onRentingRadioButtonClicked(view)
            R.id.sellingRadioButton -> if (view is RadioButton) onSellingRadioButtonClicked(view)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) { }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (view is TextView) viewModel.ad.hood = view.text.toString()
    }

    private fun initUI() {
        setQuartosSeekBarProgress(progress = viewModel.ad.rooms)
        setWCsSeekBarProgress(progress = viewModel.ad.wcs)
        binding.include2.roomsSeekBar.setOnSeekBarChangeListener(this)
        binding.include2.wcsSeekBar.setOnSeekBarChangeListener(this)
        binding.include4.waterCheckBox.isChecked = viewModel.ad.hasWater
        binding.include4.lightCheckBox.isChecked = viewModel.ad.hasLight
        binding.include4.furnitureCheckBox.isChecked = viewModel.ad.hasFurniture
        binding.publicarMaterialButton.setOnClickListener(this)
        initHoodsSpinner()
    }

    private fun checkPrice(): Boolean {
        binding.include5.priceTextInputLayout.editText?.text.toString()
            .also { priceText -> if (priceText.isEmpty()) return false }
            .toIntOrNull()
            ?.let { price ->
                viewModel.ad.price = price
                return true
            }
        return false
    }

    private fun checkSeekBar(): Boolean {
        if (binding.include2.roomsSeekBar.progress == 0) {
            displaySnackbar(message = "A casa deve ter pelo menos 1 quarto!")
            return false
        }
        if (binding.include2.wcsSeekBar.progress == 0) {
            displaySnackbar(message = "A casa deve ter pelo menos 1 WC!")
            return false
        }
        return true
    }

    private fun setQuartosSeekBarProgress(progress: Int) {
        viewModel.ad.rooms = progress
        binding.include2.roomsSeekBar.progress = progress
        binding.include2.roomsTextView.text = "${progress}x Quartos"
    }

    private fun setWCsSeekBarProgress(progress: Int) {
        viewModel.ad.wcs = progress
        binding.include2.wcsSeekBar.progress = progress
        binding.include2.wcsTextView.text = "${viewModel.ad.wcs}x WCs"
    }

    private fun createAD() = lifecycleScope.launchWhenStarted {
        binding.publicarMaterialButton.visibility = View.GONE
        displaySnackbar(message = "Publicando o anúncio . . . ")
        val isADCreated = viewModel.createAD()
        if (isADCreated) {
            binding.publicarMaterialButton.findNavController()
                .navigateUp()
            return@launchWhenStarted
        }
        binding.publicarMaterialButton.visibility = View.VISIBLE
        displaySnackbar(message = "Não foi possível publicar, tente mais tarde!")
    }


    private fun onPublishingButtonClicked() {
        if (checkSeekBar() && checkPrice()) createAD()
        else binding.include5.priceTextInputLayout.error = "O campo não foi preenchido corretamente!"
    }

    private fun onRentingRadioButtonClicked(radioButton: RadioButton) {
        if (radioButton.isChecked) viewModel.ad.type = ADsService.AD_TYPE_RENTING
    }

    private fun onSellingRadioButtonClicked(radioButton: RadioButton) {
        if (radioButton.isChecked) viewModel.ad.type = ADsService.AD_TYPE_RENTING
    }

    private fun initHoodsSpinner() = lifecycleScope.launchWhenStarted {
    val hoods = viewModel.retrieveZonas()
        context?.let {
            val adapter = ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, hoods)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.include5.hoodsSpinner.adapter = adapter
        }
        binding.include5.hoodsSpinner.onItemSelectedListener = this@CreateADFragment
    }

}
