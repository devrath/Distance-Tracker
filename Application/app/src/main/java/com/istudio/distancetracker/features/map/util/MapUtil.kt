package com.istudio.distancetracker.features.map.util

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import java.text.DecimalFormat

object MapUtil {

    fun setCameraPosition(location: LatLng): CameraPosition {
        return CameraPosition.Builder()
            .target(location)
            .zoom(18f)
            .build()
    }

    fun calculateTheDistance(locationList: MutableList<LatLng>): String {
        val firstPoint = locationList.first()
        val lastPoint = locationList.last()

        if(locationList.size > 1){
            // Meters
            val meters = SphericalUtil.computeDistanceBetween(firstPoint,lastPoint)
            // Kilometers
            val kilometers = meters / 1000
            return DecimalFormat("#.##").format(kilometers)
        }
        return "0.00"
    }

}