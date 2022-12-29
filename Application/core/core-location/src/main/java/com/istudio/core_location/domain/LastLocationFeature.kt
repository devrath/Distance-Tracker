package com.istudio.core_location.domain

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface LastLocationFeature {
    suspend fun currentLocation() : Flow<Location>
}
