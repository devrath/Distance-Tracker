package com.istudio.distancetracker.features.map.presentation.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.istudio.core_common.extensions.gone
import com.istudio.core_common.extensions.visible
import com.istudio.distancetracker.databinding.IncludeCustMasterViewBinding

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
    fun setCustomIconForLocationButton(
        darkMode: Boolean, uiModeKeyStored: Boolean, isSystemSelectionLightMode: Boolean
    ) {
        binding.mapViewId.setCustomIconForLocationButton(
            darkMode,uiModeKeyStored,isSystemSelectionLightMode)
    }
    fun initiateLocationButtonClick() { binding.mapViewId.initiateLocationButtonClick() }
    fun counterCountDownState(currentSecond: String) { binding.mapViewId.counterCountDownState(currentSecond) }

    fun showMapView(isError:Boolean=true, isGpsError:Boolean=true) {
        binding.apply {
            if(isError){
                mapViewId.gone(animate = false)
                errorViewId.visible(animate = false)
                if(isGpsError){
                    errorViewId.noGpsView()
                }else{
                    errorViewId.noConnectivityView()
                }
            }else{
                mapViewId.visible(animate = false)
                errorViewId.gone(animate = false)
            }
        }
    }

    fun toggleUiMode() { binding.apply { mapViewId.toggleUiMode() } }
    fun initialActionButtonSetUp(isDarkMode: Boolean) { binding.apply { mapViewId.initialActionButtonSetUpForMap(isDarkMode) } }

    fun setFabGalleryClickListener(listener: OnClickListener) = binding.mapViewId.setFabGalleryClickListener(listener)
    fun setChangeStyleButtonClickListener(listener: OnClickListener) = binding.mapViewId.setChangeStyleButtonClickListener(listener)
    fun setFabButtonClickListener(listener: OnClickListener) = binding.mapViewId.setFabButtonClickListener(listener)
    fun setUiModeFabButtonClickListener(listener: OnClickListener) = binding.mapViewId.setUiModeFabButtonClickListener(listener)
    fun setStartButtonClickListener(listener: OnClickListener) = binding.mapViewId.setStartButtonClickListener(listener)
    fun setStopButtonClickListener(listener: OnClickListener) = binding.mapViewId.setStopButtonClickListener(listener)
    fun setResetButtonClickListener(listener: OnClickListener) = binding.mapViewId.setResetButtonClickListener(listener)
    fun setActLstButtonClickListener(listener: OnClickListener) = binding.mapViewId.setActLstButtonClickListener(listener)
    fun setLocationSettingsButtonClickListener(listener: OnClickListener) = binding.errorViewId.setGpsSettingsActionListener(listener)
    fun setNetworkSettingsButtonClickListener(listener: OnClickListener) = binding.errorViewId.setNetworkSettingsActionListener(listener)

}