package com.example.footballboard.screen.navigationbar

import android.content.Context
import android.os.Build
import android.view.Window
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.footballboard.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(window: Window, context: Context) {
    // Raise the elements above the keyboard
    var shouldResize = false // False will resize
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.setDecorFitsSystemWindows(shouldResize)
        shouldResize = shouldResize.not()
    } else {
        if (shouldResize.not())
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        else
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    val teamName = rememberSaveable { mutableStateOf("") }

    Scaffold(content = { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            verticalArrangement = Arrangement.Top
        ) {
            Row(Modifier.fillMaxWidth()) {
                // OutlinedTextField to type the team name
                OutlinedTextField(
                    value = teamName.value,
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                    onValueChange = { teamName.value = it },
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    label = { Text(text = context.getString(R.string.team_name)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    )
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search button")
                }
            }
        }
    })
}