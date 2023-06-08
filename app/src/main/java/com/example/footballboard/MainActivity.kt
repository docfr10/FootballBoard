package com.example.footballboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.footballboard.screen.AppScreen
import com.example.footballboard.ui.theme.FootballBoardTheme
import com.example.footballboard.viewModel.AuthenticationViewModel
import com.example.footballboard.viewModel.CompetitionsInterestViewModel
import com.example.footballboard.viewModel.ProfileViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // Firebase objects
    private val auth = FirebaseAuth.getInstance()
    private val databaseInstance = FirebaseDatabase.getInstance()
    private var cUser = auth.currentUser
    private val signInWithGoogleLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account.idToken!!)
                }
            } catch (e: ApiException) {
                Log.d("LogApiException", e.toString())
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FootballBoardTheme {
                // ViewModel objects
                val authenticationViewModel = hiltViewModel<AuthenticationViewModel>()
                val competitionsInterestViewModel = hiltViewModel<CompetitionsInterestViewModel>()
                val profileViewModel = hiltViewModel<ProfileViewModel>()

                AppScreen(
                    auth = auth,
                    authenticationViewModel = authenticationViewModel,
                    competitionsInterestViewModel = competitionsInterestViewModel,
                    cUser = cUser,
                    databaseInstance = databaseInstance,
                    signInWithGoogleLauncher = signInWithGoogleLauncher,
                    profileViewModel = profileViewModel,
                    window = window
                )
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                cUser = auth.currentUser
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}