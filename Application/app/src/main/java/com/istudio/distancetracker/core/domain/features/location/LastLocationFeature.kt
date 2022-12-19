package com.istudio.distancetracker.core.domain.features.connectivity

import com.istudio.distancetracker.core.domain.models.User

interface ConnectivityFeature {
    fun checkConnectivity(): Boolean
}
