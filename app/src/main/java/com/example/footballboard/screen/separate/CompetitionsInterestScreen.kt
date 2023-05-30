package com.example.footballboard.screen.separate

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.footballboard.R
import com.example.footballboard.network.MainApi
import com.example.footballboard.network.competitionModel.Competition
import com.example.footballboard.utils.Routes.HOME_SCREEN
import com.example.footballboard.viewModel.CompetitionsInterestViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompetitionsInterestScreen(
    mainApi: MainApi,
    context: Context,
    competitionsInterestViewModel: CompetitionsInterestViewModel,
    animatedNavController: NavHostController
) {
    val competitions = remember { mutableStateOf<List<Competition>?>(null) }
    val selectedCompetitions = remember { mutableStateOf(emptyList<Int>()) }

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            competitions.value = mainApi.getAllCompetitions().competitions
        }
    }

    Scaffold(content = { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 10.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(text = context.getString(R.string.competitions_interest), fontSize = 16.sp)
            }
            competitions.value?.let {
                items(competitions.value!!) {
                    val index = competitions.value!!.indexOf(it)
                    val isSelected = selectedCompetitions.value.contains(index)

                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.onSurface
                                    else MaterialTheme.colorScheme.surface
                                )
                                .clickable {
                                    selectedCompetitions.value = if (isSelected) {
                                        selectedCompetitions.value - index
                                    } else {
                                        selectedCompetitions.value + index
                                    }
                                },
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                text = it.name,
                                color = if (isSelected) MaterialTheme.colorScheme.surface
                                else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
        }
    }, floatingActionButton = {
        if (selectedCompetitions.value.isNotEmpty())
        // Button to go to the selection of competitions
            FloatingActionButton(shape = CircleShape, onClick = {
                competitionsInterestViewModel.addCompetitionsInterest(selectedCompetitions.value)
                animatedNavController.navigate(HOME_SCREEN) {
                    popUpTo(animatedNavController.graph.id) { inclusive = true }
                }
            }) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Done")
            }
    })
}