package com.istudio.distancetracker

import android.annotation.SuppressLint
import android.graphics.Color
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.istudio.distancetracker.databinding.FragmentMapBinding
import com.istudio.distancetracker.databinding.FragmentPermissionBinding
import com.istudio.distancetracker.utils.Permissions.hasBackgroundLocationPermission
import com.istudio.distancetracker.utils.Permissions.isBackgroundPermissionRequired
import com.istudio.distancetracker.utils.Permissions.permissionBackgroundLocation
import com.istudio.distancetracker.utils.hide
import com.istudio.distancetracker.utils.openAppNotificationSettings
import com.istudio.distancetracker.utils.show
import com.permissionx.guolindev.PermissionX
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapFragment : Fragment(), GoogleMap.OnMyLocationButtonClickListener {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var map: GoogleMap

    // ********************************** Life cycle methods ***************************************
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View { return initOnCreateView(inflater,container) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOnViewCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        initOnDestroyView()
    }
    // ********************************** Life cycle methods ***************************************

    // ********************************** Over-ridden methods **************************************
    override fun onMyLocationButtonClick(): Boolean {
        // Perform animation and fade the text view after a certain duration
        binding.apply {
            hintTextView.animate().alpha(0f).duration = 1500
            lifecycleScope.launch {
                // Provide a delay
                delay(2500)
                // Finally hide the view
                hintTextView.hide()
                // Display the start button
                startButton.show()
            }
        }
        return true
    }
    // ********************************** Over-ridden methods **************************************

    // ********************************** User defined functions ************************************
    private fun initOnCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initOnViewCreated() {
        initiateMapSync()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.apply {
            startButton.setOnClickListener { startButtonAction() }
            stopButton.setOnClickListener {

            }
            resetButton.setOnClickListener {

            }
        }
    }

    private fun startButtonAction() {
        requestPermission()
    }

    private fun initiateMapSync() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun initOnDestroyView() { _binding = null }
    // ********************************** User defined functions ************************************

    // **********************************CallBacks *************************************************
    @SuppressLint("MissingPermission","PotentialBehaviorOverride")
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        map.isMyLocationEnabled = true
        map.setOnMyLocationButtonClickListener(this)
        map.uiSettings.apply {
            // By setting the below permissions we don't give the option for user to move the maps instead we move the camera ourselves
            isZoomControlsEnabled = false
            isZoomGesturesEnabled = false
            isRotateGesturesEnabled = false
            isTiltGesturesEnabled = false
            isCompassEnabled = false
            isScrollGesturesEnabled = false
        }

    }
    // **********************************CallBacks *************************************************

    private fun requestPermission(){

        if(hasBackgroundLocationPermission(requireContext())){
           // Proceed further

        }else{
            PermissionX.init(this)
                .permissions(permissionBackgroundLocation)
                .setDialogTintColor(Color.parseColor("#1972e8"), Color.parseColor("#8ab6f5"))
                .onExplainRequestReason { scope, deniedList, beforeRequest ->
                    val message = requireActivity().getText(R.string.str_provide_permissions).toString()
                    scope.showRequestReasonDialog(
                        deniedList, message,
                        requireActivity().getText(R.string.str_allow).toString(),
                        requireActivity().getText(R.string.str_deny).toString())
                }
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) { startButtonAction() }
                    else {
                        Snackbar.make(binding.root, requireActivity().getText(R.string.str_location_permission_required), Snackbar.LENGTH_LONG)
                            .setAction(requireActivity().getText(R.string.str_location)) {
                                requireActivity().openAppNotificationSettings()

                            }
                            .show()
                    }
                }
        }

    }



}