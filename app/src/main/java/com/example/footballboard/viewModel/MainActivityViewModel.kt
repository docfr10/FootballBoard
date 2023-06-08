package com.example.footballboard.viewModel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.footballboard.MainActivity
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val application: Application) :
    ViewModel() {
    // Firebase objects
    private val auth = FirebaseAuth.getInstance()
    private val databaseInstance = FirebaseDatabase.getInstance()
    private val cUser = auth.currentUser

    fun getAuth() = auth
    fun getDatabaseInstance() = databaseInstance
    fun getCUser() = cUser

    fun signInWithCredential(credential: AuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                application.startActivity(Intent(application, MainActivity::class.java))
            }
        }
    }
}
