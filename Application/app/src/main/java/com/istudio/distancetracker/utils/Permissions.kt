package com.istudio.distancetracker.utils

import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker

object Permissions {

    // <-------------------------------- Location Permission -------------------------------------->
    private const val permissionLocation = ACCESS_FINE_LOCATION

    private const val granted = PermissionChecker.PERMISSION_GRANTED

    fun hasLocationPermission(context:Context) = ContextCompat.checkSelfPermission(context, permissionLocation) == granted
    // <-------------------------------- Location Permission -------------------------------------->


    // <-------------------------------- BackGround Permission ------------------------------------>
    @RequiresApi(Build.VERSION_CODES.Q)
    const val permissionBackgroundLocation = ACCESS_BACKGROUND_LOCATION

    fun isBackgroundPermissionRequired(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    fun hasBackgroundLocationPermission(context:Context): Boolean {
        return if(isBackgroundPermissionRequired()){
            // The permission ACCESS_BACKGROUND_LOCATION is needed from android-10 and above, So check it
            ContextCompat.checkSelfPermission(context, permissionBackgroundLocation) == granted
        }else{
            // In older version its by default provided
            true
        }
    }
    // <-------------------------------- BackGround Permission ------------------------------------>


}