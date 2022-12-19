package com.istudio.distancetracker.features.map.domain.entities.inputs

import com.google.android.gms.maps.model.LatLng

data class CalculateResultInput(
    val locationData: MutableList<LatLng>,
    val startTime: Long,
    val stopTime: Long
)
