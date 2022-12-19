package com.istudio.distancetracker.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.istudio.distancetracker.R
import com.istudio.distancetracker.core.platform.extensions.gone
import com.istudio.distancetracker.core.platform.extensions.visible
import com.istudio.distancetracker.databinding.IncludeCustMasterViewBinding
import com.istudio.distancetracker.utils.enable

class MapMasterView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributes, defStyleAttr) {

    companion object {
        const val TAG = "MapMasterView"
    }

    private var binding =
        IncludeCustMasterViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun displayStartButton() { binding.mapViewId.displayStartButton() }
    fun disableStartButton() { binding.mapViewId.disableStartButton() }
    fun enableStopButton() { binding.mapViewId.enableStopButton() }
    fun resetMapUiState() { binding.mapViewId.resetMapUiState() }
    fun counterGoState() { binding.mapViewId.counterGoState() }
    fun countDownUiState() { binding.mapViewId.countDownUiState() }
    fun stoppedUiState() { binding.mapViewId.stoppedUiState() }
    fun hideTimerTextView() { binding.mapViewId.hideTimerTextView() }
    fun startButtonActionUiState() { binding.mapViewId.startButtonActionUiState() }
    fun setCustomIconForLocationButton() { binding.mapViewId.setCustomIconForLocationButton() }
    fun initiateLocationButtonClick() { binding.mapViewId.initiateLocationButtonClick() }
    fun counterCountDownState(currentSecond: String) { binding.mapViewId.counterCountDownState(currentSecond) }

    fun showMapView(isError:Boolean=true, isGpsError:Boolean=true) {
        binding.apply {
            if(isError){
                mapViewId.gone(animate = true)
                errorViewId.visible(animate = true)
                if(isGpsError){
                    errorViewId.noGpsView()
                }else{
                    errorViewId.noConnectivityView()
                }
            }else{
                mapViewId.visible(animate = true)
                errorViewId.gone(animate = true)
            }
        }
    }

    fun setStartButtonClickListener(listener: OnClickListener) = binding.mapViewId.setStartButtonClickListener(listener)
    fun setStopButtonClickListener(listener: OnClickListener) = binding.mapViewId.setStopButtonClickListener(listener)
    fun setResetButtonClickListener(listener: OnClickListener) = binding.mapViewId.setResetButtonClickListener(listener)
    fun setActLstButtonClickListener(listener: OnClickListener) = binding.mapViewId.setActLstButtonClickListener(listener)
    fun setLocationSettingsButtonClickListener(listener: OnClickListener) = binding.errorViewId.setGpsSettingsActionListener(listener)

}