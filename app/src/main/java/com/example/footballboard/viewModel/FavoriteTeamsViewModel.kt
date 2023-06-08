package com.example.footballboard.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballboard.network.MainApi
import com.example.footballboard.network.teamModel.Team
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteTeamsViewModel @Inject constructor(private val mainApi: MainApi) : ViewModel() {
    private val databaseReference = FirebaseDatabase.getInstance()
        .getReference("USERS/${FirebaseAuth.getInstance().uid}/TeamsInterest")

    suspend fun getAllTeams(): List<Team> {
        return mainApi.getAllTeams().teams
    }

    fun addTeamsInterest(selectedTeams: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            selectedTeams.forEach {
                databaseReference.child(it.toString()).setValue(it.toString())
            }
        }
    }
}