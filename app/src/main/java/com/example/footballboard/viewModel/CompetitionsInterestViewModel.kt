package com.example.footballboard.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballboard.network.MainApi
import com.example.footballboard.network.competitionModel.Competition
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompetitionsInterestViewModel @Inject constructor(private val mainApi: MainApi) :
    ViewModel() {
    private val databaseReference = FirebaseDatabase.getInstance()
        .getReference("USERS/${FirebaseAuth.getInstance().uid}/CompetitionsInterest")

    suspend fun getAllCompetitions(): List<Competition> {
        return mainApi.getAllCompetitions().competitions
    }

    fun addCompetitionsInterest(selectedCompetitions: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            selectedCompetitions.forEach {
                databaseReference.child(it.toString()).setValue(it.toString())
            }
        }
    }
}