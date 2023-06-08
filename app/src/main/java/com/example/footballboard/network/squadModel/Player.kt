package com.example.footballboard.network.squadModel

import com.example.footballboard.network.coachModel.Contract

data class Player(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val name: String,
    val position: String,
    val dateOfBirth: String,
    val nationality: String,
    val shirtNumber: Int,
    val marketValue: Int,
    val contract: Contract
)