package com.demo.feat_repository

import com.demo.core_network.api.DistanceTrackerApi
import com.istudio.core_database.data.repository.DistanceTrackerDbRepository
import javax.inject.Inject

class DistanceTrackerRepository @Inject constructor(
    private val distanceTrackerApi : DistanceTrackerApi,
    private val distanceTrackerDbRepository : DistanceTrackerDbRepository
) {

}