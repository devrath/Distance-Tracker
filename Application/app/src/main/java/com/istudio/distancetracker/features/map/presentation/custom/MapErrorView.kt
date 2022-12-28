package com.istudio.distancetracker.features.map.presentation.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.istudio.core_common.extensions.gone
import com.istudio.core_common.extensions.visible
import com.istudio.distancetracker.R
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
            wifiErrorImg.visible(animate = false)
            // Hide the gps error
            gpsErrorImg.gone(animate = false)
            // Show the network settings action button
            networkSettingsActionId.visible(animate = false)
            // Hide the gps settings action button
            gpsSettingsActionId.gone(animate = false)
            // Set the text
            notificationTxtId.text = resources.getText(R.string.str_network_warning)
        }
    }

    fun noGpsView() {
        binding.apply {
            // Show the network error
            wifiErrorImg.gone(animate = false)
            // Hide the gps error
            gpsErrorImg.visible(animate = false)
            // Hide the network settings action button
            networkSettingsActionId.gone(animate = false)
            // Show the gps settings action button
            gpsSettingsActionId.visible(animate = false)
            // Set the text
            notificationTxtId.text = resources.getText(R.string.str_gps_warning)
        }
    }

    fun setNetworkSettingsActionListener(listener: OnClickListener) =
        binding.networkSettingsActionId.setOnClickListener(listener)

    fun setGpsSettingsActionListener(listener: OnClickListener) =
        binding.gpsSettingsActionId.setOnClickListener(listener)

}