package com.example.footballboard.network.currentSeasonModel

data class CurrentSeason(
    val id: Int,
    val startDate: String,
    val endDate: String,
    val currentMatchday: Int,
    val winner: Any?
)
