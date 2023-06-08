package com.example.footballboard.network.coachModel

data class Coach(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val name: String,
    val dateOfBirth: String,
    val nationality: String,
    val contract: Contract
)
