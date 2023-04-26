package com.example.footballboard.screen.navigationbar

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.footballboard.R
import com.example.footballboard.viewModel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@Composable
fun ProfileScreen(
    context: Context,
    cUser: FirebaseUser?,
    auth: FirebaseAuth,
    animatedNavController: NavHostController,
    profileViewModel: ProfileViewModel
) {
    val touchCounter = rememberSaveable { mutableStateOf(0) }

    // Column Composable
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        // parameters set to place the items in center
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon Composable
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "profile",
            tint = MaterialTheme.colorScheme.surfaceTint,
        )
        // Text to Display the current Screen
        Text(text = context.getString(R.string.you_logged))
        Text(text = "${cUser?.email}")
        // Button to logout
        Button(onClick = {
            when (touchCounter.value) {
                0 -> {
                    profileViewModel.clickAgainToast()
                    touchCounter.value++
                }

                1 -> {
                    profileViewModel.signOut(
                        auth = auth,
                        animatedNavController = animatedNavController
                    )
                }
            }
        }, modifier = Modifier.padding(5.dp)) { Text(text = context.getString(R.string.log_out)) }
    }
}