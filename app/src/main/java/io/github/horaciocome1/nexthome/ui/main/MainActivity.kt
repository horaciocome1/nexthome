package io.github.horaciocome1.nexthome.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import io.github.horaciocome1.nexthome.R
import io.github.horaciocome1.nexthome.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var binding: ActivityMainBinding

    private val navController: NavController by lazy {
        Navigation.findNavController(this, R.id.navHostFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    override fun onStart() {
        super.onStart()
        checkAuthentication()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navigated = NavigationUI.onNavDestinationSelected(item, navController)
        return navigated || super.onOptionsItemSelected(item)
    }

    private fun initUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        navController.addOnDestinationChangedListener(this)
    }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp()

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        setDisplayHomeAsUp(destinationId = destination.id)
        setSupportActionBarVisibility(destinationId = destination.id)
    }

    private fun setDisplayHomeAsUp(destinationId: Int) = when (destinationId) {
        R.id.destination_start -> supportActionBar?.setDisplayHomeAsUpEnabled(false)
        R.id.destination_profile -> supportActionBar?.setDisplayHomeAsUpEnabled(false)
        else -> supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun checkAuthentication() {
        val isAuthenticationNeeded = FirebaseAuth.getInstance().currentUser == null
        if (isAuthenticationNeeded) viewModel.navigateToSignIn(controller = navController)
    }

    private fun setSupportActionBarVisibility(destinationId: Int) = when (destinationId) {
        R.id.destination_sign_in -> supportActionBar?.hide()
        else -> supportActionBar?.show()
    }

}
