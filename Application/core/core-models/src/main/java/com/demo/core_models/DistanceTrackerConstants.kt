package com.demo.core_models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.istudio.core_common.constants.DbConstants.TABLE_NAME_DISTANCE_TRACKER_CONSTANTS

@Entity(tableName = TABLE_NAME_DISTANCE_TRACKER_CONSTANTS)
data class DistanceTrackerConstants (
	@PrimaryKey(autoGenerate = true) val id : Int = 0,
	val isUpdatedAt : Long = System.currentTimeMillis(),
	@SerializedName("appUpdateConstantType") val appUpdateConstantType : Int,
	@SerializedName("timerDuration") val timerDuration : Int,
	@SerializedName("timerInterval") val timerInterval : Int,
	@SerializedName("locationUpdateInterval") val locationUpdateInterval : Int,
	@SerializedName("locationFastestUpdateInterval") val locationFastestUpdateInterval : Int,
	@SerializedName("locationMyselfTimerDuration") val locationMyselfTimerDuration : Int,
	@SerializedName("resultPageDisplayDuration") val resultPageDisplayDuration : Int,
	@SerializedName("mapsFollowPolylineUpdateDuration") val mapsFollowPolylineUpdateDuration : Int
)