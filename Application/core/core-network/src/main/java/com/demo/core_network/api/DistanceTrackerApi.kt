package com.demo.core_network.api

import com.demo.core_models.DistanceTrackerConstants
import retrofit2.http.GET

interface DistanceTrackerApi {
    // https://raw.githubusercontent.com/devrath/Distance-Tracker/master/Application/data.json
    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com/devrath/Distance-Tracker/master/Application/"
    }

    @GET("data.json")
    suspend fun getConstants(): DistanceTrackerConstants


}