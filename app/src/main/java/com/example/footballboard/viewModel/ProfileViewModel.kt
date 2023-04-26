package com.example.footballboard.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.footballboard.R
import com.example.footballboard.utils.Routes
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val application: Application) : ViewModel() {

    fun clickAgainToast() {
        Toast.makeText(application, application.getString(R.string.click_again), Toast.LENGTH_SHORT)
            .show()
    }

    fun signOut(auth: FirebaseAuth, animatedNavController: NavHostController) {
        auth.signOut()
        animatedNavController.navigate(Routes.AUTHENTICATION_SCREEN) {
            popUpTo(animatedNavController.graph.id) { inclusive = true }
        }
    }
}