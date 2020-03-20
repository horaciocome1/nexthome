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
import com.google.android.material.snackbar.Snackbar
import io.github.horaciocome1.nexthome.R
import io.github.horaciocome1.nexthome.data.ad.ADsService
import io.github.horaciocome1.nexthome.databinding.FragmentCreateADBinding

/**
 * Fragment responsible for handling the creation of ADs
 */
class CreateADFragment : Fragment(), OnSeekBarChangeListener, View.OnClickListener,
    AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentCreateADBinding

    private val viewModel: CreateADViewModel by lazy {
        ViewModelProvider(this)[CreateADViewModel::class.java]
    }

    private val snackBar: Snackbar by lazy {
        Snackbar.make(binding.scrollView, "", Snackbar.LENGTH_INDEFINITE)
    }

    private val toast: Toast by lazy {
        Toast.makeText(context, "", Toast.LENGTH_LONG)
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
            R.id.quartosSeekBar -> setQuartosSeekBarProgress(progress)
            R.id.suitesSeekBar -> setSuitesSeekBarProgress(progress)
            R.id.wcsSeekBar -> setWCsSeekBarProgress(progress)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) { }

    override fun onStopTrackingTouch(seekBar: SeekBar) { }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.publicarMaterialButton -> onPublicarButtonClicked()
            R.id.rentingRadioButton -> if (view is RadioButton) onRentingRadioButtonClicked(view)
            R.id.sellingRadioButton -> if (view is RadioButton) onSellingRadioButtonClicked(view)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) { }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (view is TextView) viewModel.ad.zona = view.text.toString()
    }

    private fun initUI() {
        snackBar.dismiss()
        setQuartosSeekBarProgress(progress = viewModel.ad.quartos)
        setSuitesSeekBarProgress(progress = viewModel.ad.suites)
        setWCsSeekBarProgress(progress = viewModel.ad.wcs)
        binding.include2.quartosSeekBar.setOnSeekBarChangeListener(this)
        binding.include2.suitesSeekBar.setOnSeekBarChangeListener(this)
        binding.include2.wcsSeekBar.setOnSeekBarChangeListener(this)
        binding.include4.aguaCheckBox.isChecked = viewModel.ad.hasAgua
        binding.include4.luzCheckBox.isChecked = viewModel.ad.hasLuz
        binding.include4.mobiladaCheckBox.isChecked = viewModel.ad.isMobilada
        binding.publicarMaterialButton.setOnClickListener(this)
        initZonasSpinner()
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
        if (binding.include2.quartosSeekBar.progress == 0) {
            toast.setTextAndShow(message = "A casa deve ter pelo menos 1 quarto!")
            return false
        }
        if (binding.include2.wcsSeekBar.progress == 0) {
            toast.setTextAndShow(message = "A casa deve ter pelo menos 1 WC!")
            return false
        }
        return true
    }

    private fun setQuartosSeekBarProgress(progress: Int) {
        viewModel.ad.quartos = progress
        binding.include2.quartosSeekBar.progress = progress
        binding.include2.quartosTextView.text = "${progress}x Quartos"
    }

    private fun setSuitesSeekBarProgress(progress: Int) {
        viewModel.ad.suites = progress
        binding.include2.suitesSeekBar.progress = progress
        binding.include2.suitesTextView.text = "${progress}x Suites"
    }

    private fun setWCsSeekBarProgress(progress: Int) {
        viewModel.ad.wcs = progress
        binding.include2.wcsSeekBar.progress = progress
        binding.include2.wcsTextView.text = "${viewModel.ad.wcs}x WCs"
    }

    private fun createAD() = lifecycleScope.launchWhenStarted {
        binding.publicarMaterialButton.visibility = View.GONE
        snackBar.setTextAndShow(message = "Publicando eu anúncio . . . ")
        val isADCreated = viewModel.createAD()
        if (isADCreated) {
            binding.publicarMaterialButton.findNavController()
                .navigateUp()
            return@launchWhenStarted
        }
        binding.publicarMaterialButton.visibility = View.VISIBLE
        snackBar.setTextAndShow(message = "Não foi possível publicar, tente mais tarde!")
    }

    private fun Snackbar.setTextAndShow(message: CharSequence) = setText(message)
        .show()

    private fun Toast.setTextAndShow(message: CharSequence) {
        setText(message)
        show()
    }


    private fun onPublicarButtonClicked() {
        snackBar.dismiss()
        if (checkSeekBar() && checkPrice()) createAD()
        else binding.include5.priceTextInputLayout.error = "O campo não foi preenchido corretamente!"
    }

    private fun onRentingRadioButtonClicked(radioButton: RadioButton) {
        if (radioButton.isChecked) viewModel.ad.type = ADsService.AD_TYPE_RENTING
    }

    private fun onSellingRadioButtonClicked(radioButton: RadioButton) {
        if (radioButton.isChecked) viewModel.ad.type = ADsService.AD_TYPE_RENTING
    }

    private fun initZonasSpinner() = lifecycleScope.launchWhenStarted {
        val zonas = viewModel.retrieveZonas()
        context?.let {
            val adapter = ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, zonas)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.include5.zonasSpinner.adapter = adapter
        }
        binding.include5.zonasSpinner.onItemSelectedListener = this@CreateADFragment
    }

}
