package io.github.horaciocome1.nexthome.ui.main

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import io.github.horaciocome1.nexthome.R
import io.github.horaciocome1.nexthome.data.auth.AuthService

class MainViewModel : ViewModel() {

    private val service: AuthService by lazy { AuthService() }

    fun amNotAuthenticated() = service.amNotAuthenticated()

    fun navigateToSignIn(controller: NavController) =
        controller.navigate(R.id.destination_sign_in)

}