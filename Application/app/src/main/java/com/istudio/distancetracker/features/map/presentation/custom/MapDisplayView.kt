package com.istudio.distancetracker.features.map.presentation.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.istudio.core_common.extensions.disable
import com.istudio.core_common.extensions.enable
import com.istudio.core_common.extensions.gone
import com.istudio.core_common.extensions.visible
import com.istudio.core_ui.R
import com.istudio.distancetracker.databinding.IncludeCustMapViewBinding

class MapDisplayView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributes, defStyleAttr) {

    companion object {
        const val TAG = "MapDisplayView"
    }

    private var binding =
        IncludeCustMapViewBinding.inflate(LayoutInflater.from(context), this, true)

    // to check whether sub FABs are visible or not
    private var isAllFabsVisible: Boolean = false

    fun displayStartButton() {
        binding.apply {
            resetButton.gone(animate = false)
            startButton.visible(animate = false)
        }
    }

    fun enableStopButton() { binding.stopButton.enable() }
    fun disableStartButton() { binding.startButton.disable() }

    fun resetMapUiState() {
        binding.apply {
            startButton.apply {
                gone(animate = false)
                enable()
            }
            stopButton.gone(animate = false)
            resetButton.visible(animate = false)
        }
    }

    fun counterGoState() {
        // Text Color:-> BLACK
        val colorBlack = ContextCompat.getColor(context, R.color.colorCounter)
        // Text String:-> GO
        val strGo = context.getText(R.string.go)
        binding.apply {
            timerTextView.text = strGo
            timerTextView.setTextColor(colorBlack)
        }
    }

    fun counterCountDownState(currentSecond: String) {
        // Text Color:-> RED
        val colorRed = ContextCompat.getColor(context, R.color.colorCounter)
        binding.apply {
            // Text String:-> Current second Number
            timerTextView.text = currentSecond.toString()
            timerTextView.setTextColor(colorRed)
        }
    }

    fun countDownUiState() {
        binding.apply {
            timerTextView.visible(animate = false)
            stopButton.disable()
        }
    }

    fun stoppedUiState() {
        binding.apply {
            stopButton.gone(animate = false)
            startButton.visible(animate = false)
        }
    }

    fun hideTimerTextView() {
        binding.timerTextView.gone(animate = false)
    }

    fun startButtonActionUiState() {
        binding.apply {
            startButton.disable()
            startButton.gone(animate = false)
            stopButton.visible(animate = false)
        }
    }

    fun setCustomIconForLocationButton(
        darkMode: Boolean, uiModeKeyStored: Boolean, isSystemSelectionLightMode:Boolean
    ) {
        val btnMyLocation: ImageView =
            (binding.map.findViewById<View>(Integer.parseInt("1"))?.parent as View).findViewById(
                Integer.parseInt("2")
            )
        btnMyLocation.apply {
            if(uiModeKeyStored){
                when {
                    darkMode -> setImageResource(R.drawable.ic_current_location_dark_mode)
                    else -> setImageResource(R.drawable.ic_current_location)
                }
            }else{
                when {
                    isSystemSelectionLightMode -> setImageResource(R.drawable.ic_current_location)
                    else -> setImageResource(R.drawable.ic_current_location_dark_mode)
                }
            }
            callOnClick();
        }
    }

    fun initiateLocationButtonClick() {
        val btnMyLocation: ImageView =
            (binding.map.findViewById<View>(Integer.parseInt("1"))?.parent as View).findViewById(
                Integer.parseInt("2")
            )
        btnMyLocation.apply { callOnClick(); }
    }

    /**
     * First time when the map is loaded, This is the initial state of the screen
     */
    fun initialActionButtonSetUpForMap(isDarkMode: Boolean) {
        // Now set all the FABs and all the action name
        // texts as GONE
        binding.uiModeFab.visibility = View.GONE;
        //mAddPersonFab.setVisibility(View.GONE);
        binding.addAlarmActionText.visibility = View.GONE;
        // binding.addPersonActionText.visibility = View.GONE;
        // invisible
        isAllFabsVisible = false;
        // Set the current icon for the action mode on the basis that saved mode is dark-mode or light-mode
        if(isDarkMode){
            // Set the light mode icon
            binding.uiModeFab.setImageResource(R.drawable.ic_light_mode)
        }else{
            // Set the dark mode icon
            binding.uiModeFab.setImageResource(R.drawable.ic_dark_mode)
        }

        binding.addFab.shrink();
    }

    /**
     * On subsequent clicks of the action button handle the expanding and collapsing state of action button
     */
    fun toggleUiMode() {
        if (!isAllFabsVisible) {
            binding.uiModeFab.show();
            binding.addStyleFab.show();
            binding.journeyGalleryFab.show();
            binding.addAlarmActionText.visibility = View.VISIBLE;
            binding.addStyleActionText.visibility = View.VISIBLE;
            binding.journeyGalleryText.visibility = View.VISIBLE;
            binding.addFab.extend();
            isAllFabsVisible = true;
        } else {
            binding.uiModeFab.hide();
            binding.addStyleFab.hide();
            binding.journeyGalleryFab.hide();
            binding.addAlarmActionText.visibility = View.GONE;
            binding.addStyleActionText.visibility = View.GONE;
            binding.journeyGalleryText.visibility = View.GONE;
            binding.addFab.shrink();
            isAllFabsVisible = false;
        }
    }

    fun setFabGalleryClickListener(listener: OnClickListener) = binding.journeyGalleryFab.setOnClickListener(listener)
    fun setChangeStyleButtonClickListener(listener: OnClickListener) = binding.addStyleFab.setOnClickListener(listener)
    fun setFabButtonClickListener(listener: OnClickListener) = binding.addFab.setOnClickListener(listener)
    fun setUiModeFabButtonClickListener(listener: OnClickListener) = binding.uiModeFab.setOnClickListener(listener)

    fun setStartButtonClickListener(listener: OnClickListener) = binding.startButton.setOnClickListener(listener)
    fun setStopButtonClickListener(listener: OnClickListener) = binding.stopButton.setOnClickListener(listener)
    fun setResetButtonClickListener(listener: OnClickListener) = binding.resetButton.setOnClickListener(listener)
    fun setActLstButtonClickListener(listener: OnClickListener) = binding.actLstId.setOnClickListener(listener)

}