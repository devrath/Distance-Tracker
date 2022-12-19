package com.istudio.distancetracker.core.data.implementation.location

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.istudio.distancetracker.core.domain.features.connectivity.ConnectivityFeature
import com.istudio.distancetracker.core.domain.features.location.LocationFeature

class LocationFeatureImpl(
    private var loacationManager: LocationManager
) : LocationFeature {

    override fun isLocationEnabled(): Boolean {
        val isGpsProviderEnabled = loacationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkProviderEnabled = loacationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return isGpsProviderEnabled && isNetworkProviderEnabled
    }

}
