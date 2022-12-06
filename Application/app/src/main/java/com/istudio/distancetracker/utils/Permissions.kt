package com.istudio.distancetracker.utils

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker

object Permissions {

    const val permissionLocation = ACCESS_FINE_LOCATION
    private const val granted = PermissionChecker.PERMISSION_GRANTED

    fun hasLocationPermission(context:Context) = ContextCompat.checkSelfPermission(context, permissionLocation) == granted


}