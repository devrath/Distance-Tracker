package com.istudio.distancetracker.core.domain.features.location

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface LastLocationFeature {
    suspend fun currentLocation() : Flow<Location>
}
