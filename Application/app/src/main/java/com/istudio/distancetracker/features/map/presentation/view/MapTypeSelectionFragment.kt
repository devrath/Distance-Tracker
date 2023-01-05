package com.istudio.distancetracker.features.map.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.istudio.distancetracker.databinding.FragmentMapTypeBinding

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

            }
            btnHybridId.setOnClickListener {

            }
            btnSatelliteId.setOnClickListener {

            }
            btnTerrainId.setOnClickListener {

            }
        }
    }

    private fun initOnDestroyView() { _binding = null }
    // ********************************** User defined functions ************************************

}