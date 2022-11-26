package com.istudio.distancetracker.misc

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.GroundOverlay
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.istudio.distancetracker.R

class Overlays {

    private val losAngeles = LatLng(34.04692127928215, -118.24748421830992)
    private val losAngelesBounds = LatLngBounds(
        LatLng(33.91195972596177, -118.76195050322201),
        LatLng(34.38361514748239, -118.15495586666819)
    )

    fun addGroundOverlay(map: GoogleMap): GroundOverlay? {
        return map.addGroundOverlay(
            GroundOverlayOptions().apply {
                positionFromBounds(losAngelesBounds)
                image(BitmapDescriptorFactory.fromResource(R.drawable.custom_marker))
            }
        )
    }

    fun addGroundOverlayWithTag(map: GoogleMap): GroundOverlay? {
        val groundOverlay = map.addGroundOverlay(
            GroundOverlayOptions().apply {
                positionFromBounds(losAngelesBounds)
                image(BitmapDescriptorFactory.fromResource(R.drawable.custom_marker))
            }
        )
        groundOverlay?.tag = "My Tag"
        return groundOverlay
    }

}