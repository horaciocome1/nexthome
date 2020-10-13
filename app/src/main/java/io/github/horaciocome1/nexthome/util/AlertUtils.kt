package io.github.horaciocome1.nexthome.util

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.displaySnackbar(message: CharSequence) = view?.let {
    Snackbar.make(it, message, Snackbar.LENGTH_LONG)
        .show()
}