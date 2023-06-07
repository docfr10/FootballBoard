package com.example.footballboard.screen.separate

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.footballboard.R
import com.example.footballboard.utils.Routes.AUTHENTICATION_SCREEN
import com.example.footballboard.utils.Routes.COMPETITIONS_INTEREST
import com.example.footballboard.utils.Routes.HOME_SCREEN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    cUser: FirebaseUser?,
    animatedNavController: NavHostController,
    databaseInstance: FirebaseDatabase
) {
    val startAnimation = remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation.value) 1f else 0f,
        animationSpec = tween(durationMillis = 3000)
    )

    LaunchedEffect(key1 = true) {
        startAnimation.value = true
        delay(4000)
        animatedNavController.popBackStack()
        if (cUser != null) {
            databaseInstance.getReference("USERS/${FirebaseAuth.getInstance().uid}/CompetitionsInterest")
                .addListenerForSingleValueEvent(
                    object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists())
                                animatedNavController.navigate(HOME_SCREEN)
                            else
                                animatedNavController.navigate(COMPETITIONS_INTEREST)
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.e("ERROR", databaseError.toString())
                        }
                    })
        } else
            animatedNavController.navigate(AUTHENTICATION_SCREEN)
    }
    SplashScreen(alphaAnim = alphaAnim.value)
}

@Composable
fun SplashScreen(alphaAnim: Float) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(200.dp)
                .alpha(alpha = alphaAnim),
            painter = painterResource(id = R.drawable.soccer_field),
            contentDescription = "Logo icon",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}