package com.istudio.distancetracker.features.map.domain.usecases

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.istudio.core_logger.domain.LoggerFeature
import com.istudio.distancetracker.features.KeysFeatureNames
import com.istudio.distancetracker.features.map.domain.entities.inputs.CalculateResultInput
import com.istudio.distancetracker.features.map.domain.entities.outputs.CalculateResultOutput
import java.text.DecimalFormat
import javax.inject.Inject

/**
 * USE CASE:
 * <1> Calculating the distance travelled from one position to another position
 * <2> Calculating the time between two points start time and stop time
 */
class CalculateResultUseCase @Inject constructor(
    private val log: LoggerFeature,
) {

    operator fun invoke(
        input: CalculateResultInput
    ): Result<CalculateResultOutput> {
        return try {
            val distance = calculateDistanceTravelled(input.locationData)
            val time = calculateElapsedTime(input.startTime,input.stopTime)

            if(distance!=null && time!=null){
                val result = CalculateResultOutput( distanceTravelled = distance, elapsedTime = time)
                Result.success(result)
            }else{
                val errorMsg = "Null Pointer"
                log.e(KeysFeatureNames.FEATURE_MAP,errorMsg)
                Result.failure(Exception(errorMsg))
            }
        } catch (ex: Exception) {
            log.e(KeysFeatureNames.FEATURE_MAP, ex.message)
            Result.failure(ex)
        }
    }

    private fun calculateDistanceTravelled(input: MutableList<LatLng>): String? {
        try {
            val firstPoint = input.first()
            val lastPoint = input.last()

            if(input.size > 1) {
                // Meters
                val meters = SphericalUtil.computeDistanceBetween(firstPoint, lastPoint)
                // Kilometers
                val kilometers = meters / 1000
                return DecimalFormat("#.##").format(kilometers)
            }
            return "0.00"
        }catch (ex:Exception){
            log.e(KeysFeatureNames.FEATURE_MAP, ex.message)
            return null
        }
    }

    private fun calculateElapsedTime(startTime: Long,stopTime: Long): String? {
        return try {
            val elapsedTime = stopTime - startTime

            val seconds = (elapsedTime / 1000).toInt() % 60
            val minutes = (elapsedTime / (1000 * 60) % 60)
            val hours = (elapsedTime / (1000 * 60 * 60) % 24)

            "$hours:$minutes:$seconds"
        }catch (ex:Exception){
            log.e(KeysFeatureNames.FEATURE_MAP, ex.message)
            null
        }
    }

}