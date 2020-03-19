package io.github.horaciocome1.nexthome.ui.ad

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import io.github.horaciocome1.nexthome.data.ad.AD
import io.github.horaciocome1.nexthome.databinding.FragmentADBinding

/**
 * Fragment responsible for displaying AD and handling user taken actions related
 */
class ADFragment : Fragment() {

    private val viewModel: ADViewModel by lazy {
        ViewModelProvider(this)[ADViewModel::class.java]
    }

    private lateinit var binding: FragmentADBinding

    private val snackBar: Snackbar by lazy {
        Snackbar.make(binding.root, "", Snackbar.LENGTH_LONG)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentADBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onStart() {
        super.onStart()
        arguments?.let { setDataToUI(adId = ADFragmentArgs.fromBundle(it).adId) }
        checkOwnerShip()
        checkIfADIsSaved()
    }

    private fun initUI() {
        binding.include.openADButton.icon = null
        binding.include.openADButton.isEnabled = false
        binding.callMaterialButton.setOnClickListener { callOwner() }
        binding.pedirFotosMaterialButton.setOnClickListener { askForPhotos() }
        binding.saveMaterialButton.setOnClickListener { saveAD() }
        binding.unSaveMaterialButton.setOnClickListener { unSaveAD() }
        binding.deleteMaterialButton.setOnClickListener { deleteAD() }
    }
    private fun setDataToUI(adId: String) = lifecycleScope.launchWhenStarted {
        viewModel.retrieveAD(adId)
        binding.include.nomeProprietarioTextView.text = viewModel.ad.proprietario.name
        binding.include.descriptionTextView.text = viewModel.ad.buildADDescription()
        binding.include.openADButton.text = viewModel.ad.price.toString()
        binding.include.zonaTextView.text = viewModel.ad.zona
    }

    private fun checkOwnerShip() = lifecycleScope.launchWhenCreated {
        val amITheOwner = viewModel.amITheOwnerOfThisAD()
        binding.deleteMaterialButton.visibility = if (amITheOwner) View.VISIBLE else View.GONE
    }

    private fun checkIfADIsSaved() = lifecycleScope.launchWhenStarted {
        val isSaved = viewModel.isADSaved()
        binding.saveMaterialButton.visibility = if (!isSaved) View.VISIBLE else View.GONE
        binding.unSaveMaterialButton.visibility = if (isSaved) View.VISIBLE else View.GONE
    }

    private fun Snackbar.setTextAndShow(message: CharSequence) = setText(message)
        .show()

    private fun AD.buildADDescription(): String {
        var description = "${quartos}x Quartos; "
        if (suites > 0)
            description += "${suites}x Suites; "
        if (wcs > 0)
            description += "${wcs}x WCs; "
        description += "\n"
        if (hasAgua)
            description += "Com água canalizada; "
        if (hasLuz)
            description += "Tem luz; "
        if (isMobilada)
            description += "Está mobilada"
        return description
    }

    private fun callOwner() {
        val uri = "tel:${viewModel.ad.proprietario.cellPhone}".trim()
        val intent = Intent(Intent.ACTION_DIAL).apply { data = Uri.parse(uri) }
        startActivity(intent)
    }

    private fun askForPhotos() {
        val message = "Olá, ${viewModel.ad.proprietario.name}!\n" +
                "Vi o seu anúncio no NextHome, pode partilhar comigo as fotos da casa?\n\n" +
                viewModel.ad.buildADDescription()
        val uri = "smsto:${viewModel.ad.proprietario.cellPhone}".trim()
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            putExtra("sms_body", message)
            data = Uri.parse(uri)
        }
        startActivity(intent)
    }

    private fun saveAD() = lifecycleScope.launchWhenStarted {
        val isSuccessful = viewModel.saveAD()
        val message = if (isSuccessful) "Anúnio guardado!" else "Falha ao guardar anúncio!"
        snackBar.setTextAndShow(message)
    }

    private fun unSaveAD() = lifecycleScope.launchWhenStarted {
        val isSuccessful = viewModel.unSaveAD()
        val message = if (isSuccessful) "Removido dos guardados!" else "Falha ao remover dos guardados!"
        snackBar.setTextAndShow(message)
    }

    private fun deleteAD() = lifecycleScope.launchWhenStarted {
        val isSuccessful = viewModel.deleteAD()
        val message = if (isSuccessful) "Anúncio apagado!" else "Falha ao apagar anúncio!"
        snackBar.setTextAndShow(message)
    }

}
