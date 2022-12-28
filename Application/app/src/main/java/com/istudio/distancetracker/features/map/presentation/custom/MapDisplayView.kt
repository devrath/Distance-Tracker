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
import com.istudio.distancetracker.R
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
        val colorBlack = ContextCompat.getColor(context, R.color.black)
        // Text String:-> GO
        val strGo = context.getText(R.string.go)
        binding.apply {
            timerTextView.text = strGo
            timerTextView.setTextColor(colorBlack)
        }
    }

    fun counterCountDownState(currentSecond: String) {
        // Text Color:-> RED
        val colorRed = ContextCompat.getColor(context, R.color.red)
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

    fun setCustomIconForLocationButton() {
        val btnMyLocation: ImageView =
            (binding.map.findViewById<View>(Integer.parseInt("1"))?.parent as View).findViewById(
                Integer.parseInt("2")
            )
        btnMyLocation.apply {
            setImageResource(R.drawable.ic_current_location)
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

    fun setStartButtonClickListener(listener: OnClickListener) = binding.startButton.setOnClickListener(listener)
    fun setStopButtonClickListener(listener: OnClickListener) = binding.stopButton.setOnClickListener(listener)
    fun setResetButtonClickListener(listener: OnClickListener) = binding.resetButton.setOnClickListener(listener)
    fun setActLstButtonClickListener(listener: OnClickListener) = binding.actLstId.setOnClickListener(listener)

}