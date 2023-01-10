package com.demo.feat_repository

import com.demo.core_network.api.DistanceTrackerApi
import javax.inject.Inject

class DistanceTrackerRepository @Inject constructor(
    private val distanceTrackerApi : DistanceTrackerApi,
    private val distanceTrackerDbRepository : DistanceTrackerRepository
) {

}