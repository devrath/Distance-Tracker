package com.istudio.distancetracker

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import com.istudio.distancetracker.Constants.PERMISSION_LOCATION_REQUEST_CODE

object Permissions {

    const val permissionLocation = ACCESS_FINE_LOCATION
    private const val granted = PermissionChecker.PERMISSION_GRANTED

    fun hasLocationPermission(context:Context) = ContextCompat.checkSelfPermission(context, permissionLocation) == granted


}