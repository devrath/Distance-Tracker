package com.istudio.distancetracker.core.data.implementation.location

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.istudio.distancetracker.core.domain.features.location.LastLocationFeature
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.asDeferred

@SuppressLint("MissingPermission")
class LastLocationFeatureImpl(
    private var fusedLocationProviderClient: FusedLocationProviderClient,
) : LastLocationFeature {

    override suspend fun currentLocation(): Flow<Location> {
        return lastLocationFlow
    }

    private val lastLocationFlow: Flow<Location> = flow {
        emit(fusedLocationProviderClient.lastLocation.asDeferred().await())
    }

}