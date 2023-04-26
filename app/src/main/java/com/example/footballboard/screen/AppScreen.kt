package com.example.footballboard.screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.footballboard.screen.navigationbar.HomeScreen
import com.example.footballboard.screen.separate.AnimatedSplashScreen
import com.example.footballboard.utils.Routes.HOME_SCREEN
import com.example.footballboard.utils.Routes.SPLASH_SCREEN
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(auth: FirebaseAuth, cUser: FirebaseUser?) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Scaffold(
            content = { paddingValues ->
                NavHostContainer(auth = auth, cUser = cUser, paddingValues = paddingValues)
            }
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun NavHostContainer(
    paddingValues: PaddingValues,
    auth: FirebaseAuth,
    cUser: FirebaseUser?
) {
    val animatedNavController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = animatedNavController,
        startDestination = SPLASH_SCREEN,
        modifier = Modifier.padding(paddingValues = paddingValues),
        builder = {
            composable(route = SPLASH_SCREEN) {
                AnimatedSplashScreen(animatedNavController = animatedNavController, cUser = cUser)
            }
            composable(route = HOME_SCREEN) {
                HomeScreen()
            }
        })
}
