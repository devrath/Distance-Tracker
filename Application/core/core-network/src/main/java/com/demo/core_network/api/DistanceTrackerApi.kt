package com.demo.core_network.api

import com.demo.core_models.DistanceTrackerConstants
import retrofit2.http.GET

interface DistanceTrackerApi {

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com/devrath/Distance-Tracker/master/Application/data.json"
    }

    @GET(".")
    suspend fun getConstants(): DistanceTrackerConstants


}