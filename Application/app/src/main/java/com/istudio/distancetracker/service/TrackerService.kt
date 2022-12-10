package com.istudio.distancetracker.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.istudio.distancetracker.utils.Constants
import com.istudio.distancetracker.utils.Constants.ACTION_SERVICE_START
import com.istudio.distancetracker.utils.Constants.ACTION_SERVICE_STOP
import com.istudio.distancetracker.utils.Constants.LOCATION_FASTEST_UPDATE_INTERVAL
import com.istudio.distancetracker.utils.Constants.LOCATION_UPDATE_INTERVAL
import com.istudio.distancetracker.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.istudio.distancetracker.utils.Constants.NOTIFICATION_CHANNEL_NAME
import com.istudio.distancetracker.utils.Constants.NOTIFICATION_ID
import com.istudio.distancetracker.utils.MapUtil.calculateTheDistance
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TrackerService : LifecycleService() {

    @Inject
    lateinit var notification: NotificationCompat.Builder

    @Inject
    lateinit var notificationManager: NotificationManager

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    companion object {
        val started = MutableLiveData<Boolean>()
        val startTime = MutableLiveData<Long>()
        val stopTime = MutableLiveData<Long>()

        val locationList = MutableLiveData<MutableList<LatLng>>()
    }

    // ********************************** Life cycle methods ***************************************
    override fun onCreate() {
        initOnCreate()
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        initOnStartCommand(intent)
        return super.onStartCommand(intent, flags, startId)
    }
    // ********************************** Life cycle methods ***************************************

    // ********************************** User defined functions ************************************

    private fun initOnCreate() {
        setInitialValues()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun initOnStartCommand(intent: Intent?) {
        intent?.let {
            when (it.action) {
                ACTION_SERVICE_START -> {
                    started.postValue(true)
                    // Start the foreground service
                    initiateForegroundService()
                    // Start the location updates
                    startLocationUpdates()
                }
                ACTION_SERVICE_STOP -> {
                    started.postValue(false)
                }
                else -> {}
            }
        }
    }

    private fun setInitialValues() {
        started.postValue(false)
        startTime.postValue(0L)
        stopTime.postValue(0L)

        locationList.postValue(mutableListOf())
    }

    private fun initiateForegroundService() {
        createNotificationChannel()
        startForeground(NOTIFICATION_ID,notification.build())
    }

    private fun createNotificationChannel() {
        // There is no else condition since for lower version, we don't need the notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        // Create a location request
        /*val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, LOCATION_UPDATE_INTERVAL
        ).setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(LOCATION_FASTEST_UPDATE_INTERVAL)
            .setMaxUpdateDelayMillis(LOCATION_UPDATE_INTERVAL)
            .build()*/
        val locationRequest = LocationRequest.create().apply {
            interval = LOCATION_UPDATE_INTERVAL
            fastestInterval = LOCATION_FASTEST_UPDATE_INTERVAL
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        // Request for location using the fused location provider client
        fusedLocationProviderClient.requestLocationUpdates(
            // Pass the location request
            locationRequest,
            // Pass the callback for the location
            locationCallback,
            // Looper
            Looper.getMainLooper()
        )
        startTime.postValue(System.currentTimeMillis())
    }

    private val locationCallback = object : LocationCallback() {
        // This function will receive a new location update on each location change
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            result.locations.let { locations ->
                for (location in locations) {
                    val latLng = LatLng(location.latitude,location.longitude)
                    Log.d("TrackerService",latLng.toString())
                    //updateLocationList(location)
                    //updateNotificationPeriodically()
                }
            }
        }
    }

    private fun updateLocationList(location: Location) {
        val newLatLng = LatLng(location.latitude, location.longitude)
        locationList.value?.apply {
            add(newLatLng)
            locationList.postValue(this)
        }
    }

    private fun updateNotificationPeriodically() {
        notification.apply {
            setContentTitle("Distance Travelled")
            setContentText(locationList.value?.let { calculateTheDistance(it) } + "km")
        }
        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }
    // ********************************** User defined functions ************************************
}