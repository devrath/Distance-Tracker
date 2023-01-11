package com.demo.core_models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.istudio.core_common.constants.DbConstants.TABLE_NAME_DISTANCE_TRACKER_CONSTANTS
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = TABLE_NAME_DISTANCE_TRACKER_CONSTANTS)
data class DistanceTrackerConstants (
	@PrimaryKey(autoGenerate = true) val id : Int = 0,
	val isUpdatedAt : Long = System.currentTimeMillis(),
	@SerialName("appUpdateConstantType") val appUpdateConstantType : Int,
	@SerialName("timerDuration") val timerDuration : Int,
	@SerialName("timerInterval") val timerInterval : Int,
	@SerialName("locationUpdateInterval") val locationUpdateInterval : Int,
	@SerialName("locationFastestUpdateInterval") val locationFastestUpdateInterval : Int,
	@SerialName("locationMyselfTimerDuration") val locationMyselfTimerDuration : Int,
	@SerialName("resultPageDisplayDuration") val resultPageDisplayDuration : Int,
	@SerialName("mapsFollowPolylineUpdateDuration") val mapsFollowPolylineUpdateDuration : Int
)