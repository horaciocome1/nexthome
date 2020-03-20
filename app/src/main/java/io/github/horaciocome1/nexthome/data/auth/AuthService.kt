package io.github.horaciocome1.nexthome.data.auth

import com.google.firebase.auth.FirebaseAuth

class AuthService : AuthInterface {

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun amNotAuthenticated(): Boolean = auth.currentUser == null

}