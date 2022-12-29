package com.istudio.core_location.data

import android.location.LocationManager
import com.istudio.core_location.domain.LocationFeature

class LocationFeatureImpl(
    private var loacationManager: LocationManager
) : LocationFeature {

    override fun isLocationEnabled(): Boolean {
        val isGpsProviderEnabled = loacationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkProviderEnabled = loacationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return isGpsProviderEnabled && isNetworkProviderEnabled
    }

}
