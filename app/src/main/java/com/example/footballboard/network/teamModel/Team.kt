package com.example.footballboard.network.teamModel

import com.example.footballboard.network.areaModel.Area
import com.example.footballboard.network.competitionModel.RunningCompetitions

data class Team(
    val area: Area,
    val id: Int,
    val name: String,
    val shortName: String,
    val tla: String,
    val crest: String,
    val address: String,
    val website: String,
    val founded: Int,
    val clubColors: String,
    val venue: String,
    val runningCompetitions: RunningCompetitions,
    val marketValue: Int
)