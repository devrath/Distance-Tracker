package com.istudio.distancetracker.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import java.lang.ClassCastException


open class LocationUtility(private val activity: Activity) :
    LifecycleObserver {


    private lateinit var mLocationRequest: LocationRequest
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null

    var currentLocation = MutableLiveData<Pair<Double?, Double?>>()

    init {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
    }

    fun startLocationClient() {

        // Kick off the process of building the GoogleApiClient, LocationRequest, and
        // LocationSettingsRequest objects.
        initLocationRequest()
        checkLocationSettings()
    }


    private fun initLocationRequest() {
        mLocationRequest = LocationRequest()

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.interval = UPDATE_INTERVAL_IN_MILLISECONDS

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    /**
     * Check if the device's location settings are adequate for the app's needs using the
     * [com.google.android.gms.location.SettingsApi.checkLocationSettings] method, with the results provided through a `PendingResult`.
     */
    private fun checkLocationSettings() {

        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        builder.setAlwaysShow(true)

        val locationSettingTask = LocationServices.getSettingsClient(activity)
            .checkLocationSettings(builder.build())

        locationSettingTask.addOnCompleteListener {

            try {
                val response: LocationSettingsResponse? =
                    locationSettingTask.getResult(ApiException::class.java)
                // All location settings are satisfied. The client can initialize location
                // requests here.
                startLocationUpdates()

            } catch (exception: ApiException) {
                enableGPSDialog(exception)
            }
        }

    }


    private fun enableGPSDialog(exception: ApiException) {

        when (exception.statusCode) {

            LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                /**Location Settings available but need to turn on*/
                try {
                    val resolvable = exception as ResolvableApiException
                    resolvable.startResolutionForResult(
                        activity,
                        REQUEST_CHECK_SETTINGS
                    )
                } catch (e: Exception) {
                    when (e) {
                        is IntentSender.SendIntentException -> {
                        }
                        is ClassCastException -> {
                        }
                    }
                }
            }
            LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                /**Location settings not available on the device.*/
            }
        }
    }

    /**
     * Requests location updates from the FusedLocationApi.
     */
    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {

        if (checkPermissions()) {

            fusedLocationProviderClient?.requestLocationUpdates(
                mLocationRequest,
                locationCallback,
                Looper.getMainLooper()
            )

        } else {
            requestPermissions()
        }

    }


    private val locationCallback: LocationCallback = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            val latitude = locationResult?.locations?.get(0)?.latitude
            val longitude = locationResult?.locations?.get(0)?.longitude

            val locationInfo = Pair(latitude, longitude)
            currentLocation.value = locationInfo
        }
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    private fun stopLocationUpdates() {
        fusedLocationProviderClient?.removeLocationUpdates(locationCallback)
    }


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> when (resultCode) {
                Activity.RESULT_OK -> {
                    /**
                     * User agreed to make required location settings changes.
                     * */
                    startLocationUpdates()
                }
                Activity.RESULT_CANCELED -> {
                    /**
                     * User chose not to make required location settings changes.
                     * */
                }
            }
        }
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Granted. Start getting the location information
                startLocationUpdates()
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        startLocationUpdates()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        stopLocationUpdates()
    }


    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            REQUEST_CHECK_SETTINGS
        )
    }


    companion object {
        protected const val TAG = "MainActivity"

        /**
         * Constant used in the location settings dialog.
         */
        const val REQUEST_CHECK_SETTINGS = 0x1

        /**
         * The desired interval for location updates. Inexact. Updates may be more or less frequent.
         */
        const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 100

        /**
         * The fastest rate for active location updates. Exact. Updates will never be more frequent
         * than this value.
         */
        const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2

    }

}