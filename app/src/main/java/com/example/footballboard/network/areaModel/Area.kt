package com.example.footballboard.network.areaModel

data class Area(
    val id: String,
    val name: String,
    val countryCode: String,
    val flag: Any?,
    val parentAreaId: String,
    val parentArea: String
)