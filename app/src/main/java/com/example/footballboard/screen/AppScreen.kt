package com.example.footballboard.screen

import android.content.Context
import android.content.Intent
import android.view.Window
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.footballboard.R
import com.example.footballboard.model.navigationbar.BottomNavItemModel
import com.example.footballboard.network.MainApi
import com.example.footballboard.screen.navigationbar.FavoritesScreen
import com.example.footballboard.screen.navigationbar.HomeScreen
import com.example.footballboard.screen.navigationbar.ProfileScreen
import com.example.footballboard.screen.navigationbar.SearchScreen
import com.example.footballboard.screen.separate.AnimatedSplashScreen
import com.example.footballboard.screen.separate.AreasInterestScreen
import com.example.footballboard.screen.separate.AuthenticationScreen
import com.example.footballboard.screen.separate.CompetitionsInterestScreen
import com.example.footballboard.utils.Routes.AREAS_INTEREST
import com.example.footballboard.utils.Routes.AUTHENTICATION_SCREEN
import com.example.footballboard.utils.Routes.COMPETITIONS_INTEREST
import com.example.footballboard.utils.Routes.FAVORITES_SCREEN
import com.example.footballboard.utils.Routes.HOME_SCREEN
import com.example.footballboard.utils.Routes.PROFILE_SCREEN
import com.example.footballboard.utils.Routes.SEARCH_SCREEN
import com.example.footballboard.utils.Routes.SPLASH_SCREEN
import com.example.footballboard.viewModel.AreasInterestViewModel
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
    profileViewModel: ProfileViewModel,
    mainApi: MainApi,
    areasInterestViewModel: AreasInterestViewModel
) {
    // Hiding the bottom bar
    val isShowBottomBar = remember { mutableStateOf(false) }

    val animatedNavController = rememberAnimatedNavController()
    val context = LocalContext.current

    Surface(color = MaterialTheme.colorScheme.background) {
        Scaffold(
            bottomBar = {
                if (isShowBottomBar.value) BottomNavigationBar(
                    context = context,
                    animatedNavController = animatedNavController
                )
            },
            content = { paddingValues ->
                AnimatedNavHost(
                    navController = animatedNavController,
                    startDestination = AREAS_INTEREST,
                    modifier = Modifier.padding(paddingValues = paddingValues),
                    builder = {
                        composable(route = SPLASH_SCREEN) {
                            AnimatedSplashScreen(
                                animatedNavController = animatedNavController,
                                cUser = cUser
                            )
                            isShowBottomBar.value = false
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
                            isShowBottomBar.value = false
                        }
                        composable(route = AREAS_INTEREST,
                            exitTransition = { slideOutHorizontally(animationSpec = tween(250)) }) {
                            AreasInterestScreen(
                                areasInterestViewModel = areasInterestViewModel,
                                context = context,
                                animatedNavController = animatedNavController,
                                mainApi = mainApi
                            )
                        }
                        composable(route = COMPETITIONS_INTEREST) {
                            CompetitionsInterestScreen(context = context, mainApi = mainApi)
                        }
                        composable(route = HOME_SCREEN) {
                            HomeScreen(mainApi = mainApi)
                            isShowBottomBar.value = true
                        }
                        composable(route = FAVORITES_SCREEN) {
                            FavoritesScreen()
                            isShowBottomBar.value = true
                        }
                        composable(route = SEARCH_SCREEN) {
                            SearchScreen(context = context, window = window)
                            isShowBottomBar.value = true
                        }
                        composable(route = PROFILE_SCREEN) {
                            ProfileScreen(
                                animatedNavController = animatedNavController,
                                auth = auth,
                                context = context,
                                cUser = cUser,
                                profileViewModel = profileViewModel,
                            )
                            isShowBottomBar.value = true
                        }
                    })
            }
        )
    }
}

@Composable
private fun BottomNavigationBar(context: Context, animatedNavController: NavHostController) {
    NavigationBar(
        // Set background color
        containerColor = NavigationBarDefaults.containerColor,
        contentColor = MaterialTheme.colorScheme.contentColorFor(BottomAppBarDefaults.containerColor),
        tonalElevation = NavigationBarDefaults.Elevation,
    ) {
        // An list containing information about all NavigationBar icons
        val bottomNavItems = listOf(
            BottomNavItemModel(
                label = context.getString(R.string.home),
                icon = Icons.Filled.Home,
                route = "home"
            ),
            BottomNavItemModel(
                label = context.getString(R.string.favorites),
                icon = Icons.Filled.Favorite,
                route = "favorites"
            ),
            BottomNavItemModel(
                label = context.getString(R.string.search),
                icon = Icons.Filled.Search,
                route = "search"
            ),
            BottomNavItemModel(
                label = context.getString(R.string.profile),
                icon = Icons.Filled.Person,
                route = "profile"
            )
        )
        // Observe the backstack
        val navBackStackEntry by animatedNavController.currentBackStackEntryAsState()
        // Observe current route to change the icon
        // Color,label color when navigated
        val currentRoute = navBackStackEntry?.destination?.route
        // Bottom nav items we declared
        bottomNavItems.forEach { navItem ->
            // Place the bottom nav items
            NavigationBarItem(
                // It currentRoute is equal then its selected route
                selected = currentRoute == navItem.route,
                // Navigate on click
                onClick = { animatedNavController.navigate(navItem.route) },
                // Icon of navItem
                icon = { Icon(imageVector = navItem.icon, contentDescription = navItem.label) },
                // Label
                label = { Text(text = navItem.label) },
                alwaysShowLabel = true
            )
        }
    }
}
