package io.github.horaciocome1.nexthome.ui.profile

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.github.horaciocome1.nexthome.databinding.FragmentProfileBinding

/**
 * Fragment responsible to handle changes to User profile
 */
class ProfileFragment : BottomSheetDialogFragment() {

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        setDataToUI()
    }

    override fun onStart() {
        super.onStart()
        viewModel.name.observe(this, Observer { checkName(name = it) })
        viewModel.cellPhone.observe(this, Observer { checkCellPhone(cellPhone = it) })
    }

    override fun onDismiss(dialog: DialogInterface) {
        viewModel.updateProfile()
        super.onDismiss(dialog)
    }

    private fun initUI() {
        binding.viewModel = viewModel
        binding.meetNextHomeMaterialButton.setOnClickListener { openHome() }
    }

    private fun openHome() {
        val url = "https://www.github.com/horaciocome1/nexthome"
        val intent = Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(url) }
        startActivity(intent)
    }

    private fun setDataToUI() = lifecycleScope.launchWhenStarted {
        viewModel.retrieveProfile(context = coroutineContext)
        binding.viewModel = viewModel
    }

    private fun checkName(name: CharSequence) = if (name.isBlank())
        binding.nameTextInputLayout.error = "Este campo não pode estar vazio!"
    else
        binding.nameTextInputLayout.error = ""

    private fun checkCellPhone(cellPhone: CharSequence) = when {
        cellPhone.isBlank() ->
            binding.cellPhoneTextInputLayout.error = "Este campo não pode estar vazio!"
        cellPhone.toString().toIntOrNull() == null ->
            binding.cellPhoneTextInputLayout.error = "Este campo apenas deve conter números"
        cellPhone.toString().toInt() !in 820000000..879999999 ->
            binding.cellPhoneTextInputLayout.error = "Este campo apenas deve conter números de operadoras móveis moçambicanas válidos."
        else -> binding.cellPhoneTextInputLayout.error = ""
    }

}
