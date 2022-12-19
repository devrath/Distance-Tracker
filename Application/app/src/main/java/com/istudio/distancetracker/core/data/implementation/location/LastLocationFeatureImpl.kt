package com.istudio.distancetracker.core.data.implementation.location

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.core.RepoManager.resume
import com.istudio.distancetracker.core.domain.features.location.LastLocationFeature
import com.istudio.distancetracker.core.domain.features.location.LocationFeature
import com.istudio.distancetracker.features.map.presentation.state.MapStates
import com.istudio.distancetracker.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
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