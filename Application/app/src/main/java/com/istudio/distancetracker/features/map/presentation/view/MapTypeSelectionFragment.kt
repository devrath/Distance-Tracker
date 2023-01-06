package com.istudio.distancetracker.features.map.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.istudio.core_common.functional.PublisherEventBus
import com.istudio.distancetracker.databinding.FragmentMapTypeBinding
import com.istudio.distancetracker.features.map.events.EventMapStyleSelected
import kotlinx.coroutines.launch

class MapTypeSelectionFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentMapTypeBinding? = null
    private val binding get() = _binding!!

    var distance : String = ""
    var time : String = ""

    // ********************************** Life cycle methods ***************************************
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOnViewCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        initOnDestroyView()
    }
    // ********************************** Life cycle methods ***************************************


    // ********************************** User defined functions ************************************
    private fun initOnViewCreated() {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.apply {
            btnNormalId.setOnClickListener {
                lifecycleScope.launch {
                    PublisherEventBus.publish(EventMapStyleSelected(GoogleMap.MAP_TYPE_NORMAL))
                }
            }
            btnHybridId.setOnClickListener {
                lifecycleScope.launch {
                    PublisherEventBus.publish(EventMapStyleSelected(GoogleMap.MAP_TYPE_HYBRID))
                }
            }
            btnSatelliteId.setOnClickListener {
                lifecycleScope.launch {
                    PublisherEventBus.publish(EventMapStyleSelected(GoogleMap.MAP_TYPE_SATELLITE))
                }
            }
            btnTerrainId.setOnClickListener {
                lifecycleScope.launch {
                    PublisherEventBus.publish(EventMapStyleSelected(GoogleMap.MAP_TYPE_TERRAIN))
                }
            }
        }
    }

    private fun initOnDestroyView() { _binding = null }
    // ********************************** User defined functions ************************************

}