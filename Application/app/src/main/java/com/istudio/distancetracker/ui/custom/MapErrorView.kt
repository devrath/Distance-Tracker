package com.istudio.distancetracker.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.istudio.distancetracker.core.platform.extensions.gone
import com.istudio.distancetracker.core.platform.extensions.visible
import com.istudio.distancetracker.databinding.IncludeCustErrorViewBinding

class MapErrorView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributes, defStyleAttr) {

    companion object {
        const val TAG = "MapErrorView"
    }

    private var binding =
        IncludeCustErrorViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun noConnectivityView() {
        binding.apply {
            // Show the network error
            wifiErrorImg.visible(animate = true)
            // Hide the gps error
            gpsErrorImg.gone(animate = true)
            // Show the network settings action button
            networkSettingsActionId.visible(animate = true)
            // Hide the gps settings action button
            gpsSettingsActionId.gone(animate = true)
        }
    }

    fun noGpsView() {
        binding.apply {
            // Show the network error
            wifiErrorImg.gone(animate = true)
            // Hide the gps error
            gpsErrorImg.visible(animate = true)
            // Hide the network settings action button
            networkSettingsActionId.gone(animate = true)
            // Show the gps settings action button
            gpsSettingsActionId.visible(animate = true)
        }
    }

    fun setNetworkSettingsActionListener(listener: OnClickListener) =
        binding.networkSettingsActionId.setOnClickListener(listener)

    fun setGpsSettingsActionListener(listener: OnClickListener) =
        binding.networkSettingsActionId.setOnClickListener(listener)

}