package com.example.footballboard.network

import com.example.footballboard.network.areaModel.Areas
import com.example.footballboard.network.competitionModel.Competitions
import retrofit2.http.GET
import retrofit2.http.Headers

interface MainApi {
    @Headers("X-Auth-Token': a47ce7070d044fe9b32cb560411791f4")
    @GET("areas/")
    suspend fun getAllAreas(): Areas

    @Headers("X-Auth-Token': a47ce7070d044fe9b32cb560411791f4")
    @GET("competitions/")
    suspend fun getAllCompetitions(): Competitions
}