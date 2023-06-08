package com.example.footballboard

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.footballboard.screen.AppScreen
import com.example.footballboard.ui.theme.FootballBoardTheme
import com.example.footballboard.viewModel.AuthenticationViewModel
import com.example.footballboard.viewModel.CompetitionsInterestViewModel
import com.example.footballboard.viewModel.MainActivityViewModel
import com.example.footballboard.viewModel.ProfileViewModel
import com.example.footballboard.viewModel.FavoriteTeamsViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // MainActivity ViewModel
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    // Sing in with Google
    private val signInWithGoogleLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                mainActivityViewModel.signInWithCredential(credential)
            }
        } catch (e: ApiException) {
            Log.d("LogApiException", e.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FootballBoardTheme {
                // Others ViewModel objects
                val authenticationViewModel = hiltViewModel<AuthenticationViewModel>()
                val competitionsInterestViewModel = hiltViewModel<CompetitionsInterestViewModel>()
                val profileViewModel = hiltViewModel<ProfileViewModel>()
                val favoriteTeamsViewModel = hiltViewModel<FavoriteTeamsViewModel>()

                AppScreen(
                    authenticationViewModel = authenticationViewModel,
                    competitionsInterestViewModel = competitionsInterestViewModel,
                    mainActivityViewModel = mainActivityViewModel,
                    signInWithGoogleLauncher = signInWithGoogleLauncher,
                    profileViewModel = profileViewModel,
                    favoriteTeamsViewModel = favoriteTeamsViewModel,
                    window = window
                )
            }
        }
    }
}