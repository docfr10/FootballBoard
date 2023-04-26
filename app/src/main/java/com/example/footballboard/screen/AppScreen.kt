package com.example.footballboard.screen

import android.app.Activity
import android.content.Intent
import android.view.Window
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.footballboard.screen.navigationbar.HomeScreen
import com.example.footballboard.screen.navigationbar.ProfileScreen
import com.example.footballboard.screen.separate.AnimatedSplashScreen
import com.example.footballboard.screen.separate.AuthenticationScreen
import com.example.footballboard.utils.Routes.AUTHENTICATION_SCREEN
import com.example.footballboard.utils.Routes.HOME_SCREEN
import com.example.footballboard.utils.Routes.PROFILE_SCREEN
import com.example.footballboard.utils.Routes.SPLASH_SCREEN
import com.example.footballboard.viewModel.AuthenticationViewModel
import com.example.footballboard.viewModel.ProfileViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AppScreen(
    auth: FirebaseAuth,
    cUser: FirebaseUser?,
    window: Window,
    signInWithGoogleLauncher: ActivityResultLauncher<Intent>,
    authenticationViewModel: AuthenticationViewModel,
    profileViewModel: ProfileViewModel
) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Scaffold(
            content = { paddingValues ->
                val animatedNavController = rememberAnimatedNavController()
                val context = LocalContext.current
                val activity = LocalContext.current as Activity

                AnimatedNavHost(
                    navController = animatedNavController,
                    startDestination = SPLASH_SCREEN,
                    modifier = Modifier.padding(paddingValues = paddingValues),
                    builder = {
                        composable(route = SPLASH_SCREEN) {
                            AnimatedSplashScreen(
                                animatedNavController = animatedNavController,
                                cUser = cUser
                            )
                        }
                        composable(route = AUTHENTICATION_SCREEN) {
                            AuthenticationScreen(
                                auth = auth,
                                authenticationViewModel = authenticationViewModel,
                                animatedNavController = animatedNavController,
                                context = context,
                                signInWithGoogleLauncher = signInWithGoogleLauncher,
                                window = window
                            )
                        }
                        composable(route = HOME_SCREEN) {
                            HomeScreen()
                        }
                        composable(route = PROFILE_SCREEN) {
                            ProfileScreen(
                                animatedNavController = animatedNavController,
                                auth = auth,
                                context = context,
                                cUser = cUser,
                                profileViewModel = profileViewModel,
                            )
                        }
                    })
            }
        )
    }
}
