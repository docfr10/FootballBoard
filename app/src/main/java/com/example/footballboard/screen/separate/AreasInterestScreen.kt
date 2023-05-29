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
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import com.example.footballboard.network.areaModel.Area
import com.example.footballboard.utils.Routes.COMPETITIONS_INTEREST
import com.example.footballboard.viewModel.AreasInterestViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AreasInterestScreen(
    mainApi: MainApi,
    context: Context,
    animatedNavController: NavHostController,
    areasInterestViewModel: AreasInterestViewModel
) {
    val areas = remember { mutableStateOf<List<Area>?>(null) }
    val selectedAreas = remember { mutableStateOf(emptyList<Int>()) }

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            areas.value = mainApi.getAllAreas().areas
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
            item { Text(text = context.getString(R.string.areas_interest), fontSize = 16.sp) }
            areas.value?.let {
                items(areas.value!!) {
                    val index = areas.value!!.indexOf(it)
                    val isSelected = selectedAreas.value.contains(index)

                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.onSurface
                                    else MaterialTheme.colorScheme.surface
                                )
                                .clickable {
                                    selectedAreas.value = if (isSelected) {
                                        selectedAreas.value - index
                                    } else {
                                        selectedAreas.value + index
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
        if (selectedAreas.value.isNotEmpty())
        // Button to go to the selection of competitions
            FloatingActionButton(shape = CircleShape, onClick = {
                areasInterestViewModel.addCompetitionsInterest(selectedAreas.value)
                animatedNavController.navigate(COMPETITIONS_INTEREST)
            }) {
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Next")
            }
    })
}