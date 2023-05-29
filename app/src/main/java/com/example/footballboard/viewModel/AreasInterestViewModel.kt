package com.example.footballboard.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AreasInterestViewModel @Inject constructor() : ViewModel() {
    private val databaseReference = FirebaseDatabase.getInstance()
        .getReference("USERS/${FirebaseAuth.getInstance().uid}/AreasInterest")

    fun addCompetitionsInterest(selectedAreas: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            selectedAreas.forEach {
                databaseReference.child(it.toString()).setValue(it.toString())
            }
        }
    }
}