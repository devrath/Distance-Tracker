package com.istudio.core_location.domain

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LastLocationFeature {
    suspend fun currentLocation() : Flow<Location>
}
