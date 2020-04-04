package io.github.horaciocome1.nexthome.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import io.github.horaciocome1.nexthome.R
import io.github.horaciocome1.nexthome.databinding.FragmentSignInBinding
import kotlinx.coroutines.tasks.await

/**
 * Fragment responsible for handling the sign in process
 */
class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    private val googleSignInOptions: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    private val googleSignInClient: GoogleSignInClient by lazy {
        GoogleSignIn.getClient(activity!!, googleSignInOptions)
    }

    private val googleSignInIntent: Intent by lazy {
        googleSignInClient.signInIntent
    }

    private val snackbar: Snackbar by lazy {
        Snackbar.make(binding.contstraintLayout, "", Snackbar.LENGTH_INDEFINITE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.googleSignInMaterialButton.setOnClickListener {
            it.isEnabled = false
            startActivityForResult(googleSignInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN && data != null)
            handleSignInResult(data)
        else
            onErrorOccurred()
    }

    private fun handleSignInResult(data: Intent) = lifecycleScope.launchWhenStarted {
        try {
            GoogleSignIn.getSignedInAccountFromIntent(data)
                .await()
            snackbar.dismiss()
            findNavController()
                .navigateUp()
        } catch (exception: ApiException) { onErrorOccurred() }
    }

    private fun Snackbar.setTextAndShow(message: CharSequence) = setText(message)
        .show()

    private fun onErrorOccurred() {
        val message = "Não foi possivel iniciar sessão. Tente novamente mais tarde!"
        snackbar.setTextAndShow(message)
        binding.googleSignInMaterialButton.isEnabled = true
    }

    companion object {

        const val RC_SIGN_IN = 79

    }

}
