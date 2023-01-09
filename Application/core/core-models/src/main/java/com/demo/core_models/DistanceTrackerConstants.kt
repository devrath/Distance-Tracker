import com.google.gson.annotations.SerializedName

data class DistanceTrackerConstants (
	@SerializedName("appUpdateConstantType") val appUpdateConstantType : Int,
	@SerializedName("timerDuration") val timerDuration : Int,
	@SerializedName("timerInterval") val timerInterval : Int,
	@SerializedName("locationUpdateInterval") val locationUpdateInterval : Int,
	@SerializedName("locationFastestUpdateInterval") val locationFastestUpdateInterval : Int,
	@SerializedName("locationMyselfTimerDuration") val locationMyselfTimerDuration : Int,
	@SerializedName("resultPageDisplayDuration") val resultPageDisplayDuration : Int,
	@SerializedName("mapsFollowPolylineUpdateDuration") val mapsFollowPolylineUpdateDuration : Int
)