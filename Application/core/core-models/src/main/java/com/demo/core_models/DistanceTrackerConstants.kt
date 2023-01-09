import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "distance_tracker_constants")
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