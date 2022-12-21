package com.istudio.distancetracker

import android.graphics.Color
import com.google.android.gms.maps.model.ButtCap
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.play.core.install.model.AppUpdateType

object Constants {

    const val APP_UPDATE_TYPE = AppUpdateType.IMMEDIATE

    const val COUNTDOWN_TIMER_DURATION = 5000L
    const val COUNTDOWN_TIMER_INTERVAL = 1000L
    const val LOCATION_UPDATE_INTERVAL = 4000L
    const val LOCATION_FASTEST_UPDATE_INTERVAL = 2000L
    const val LOCATE_MYSELF_TIMER_DURATION = 5000L
    const val RESULT_PAGE_DISPLAY_DURATION = 2500L
    const val HINT_VIEW_ANIMATE_DURATION = 1500L
    const val FOLLOW_POLYLINE_UPDATE_DURATION = 1000

    const val ACTION_SERVICE_START = "ACTION_SERVICE_START"
    const val ACTION_SERVICE_STOP = "ACTION_SERVICE_STOP"
    const val ACTION_NAVIGATE_TO_MAPS_FRAGMENT = "ACTION_NAVIGATE_TO_MAPS_FRAGMENT"

    const val NOTIFICATION_CHANNEL_ID = "tracker_notification_id"
    const val NOTIFICATION_CHANNEL_NAME = "tracker_notification"
    const val NOTIFICATION_ID = 3

    const val PENDING_INTENT_REQUEST_CODE = 99

    const val APP_UPDATE_REQUEST_CODE = 100

    // <-----> Polyline constants <----->
    fun preparePolyline(locationList: MutableList<LatLng>): PolylineOptions {
        val widthValue = 10f
        val colorValue = Color.BLUE
        val typeValue = JointType.ROUND

        val polyline: PolylineOptions = PolylineOptions().apply {
            width(widthValue)
            color(colorValue)
            jointType(typeValue)
            startCap(ButtCap())
            endCap(ButtCap())
            addAll(locationList)
        }

        return polyline
    }
    // <-----> Polyline constants <----->
}