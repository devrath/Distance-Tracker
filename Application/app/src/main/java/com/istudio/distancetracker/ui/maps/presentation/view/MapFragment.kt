package com.istudio.distancetracker.ui.maps.presentation.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.istudio.distancetracker.R
import com.istudio.distancetracker.core.platform.extensions.SnackBarDisplay
import com.istudio.distancetracker.core.platform.extensions.showSnackbar
import com.istudio.distancetracker.databinding.FragmentMapBinding
import com.istudio.distancetracker.features.map.domain.entities.outputs.CalculateResultOutput
import com.istudio.distancetracker.model.Result
import com.istudio.distancetracker.service.NetworkObserver
import com.istudio.distancetracker.service.NetworkState
import com.istudio.distancetracker.service.TrackerService
import com.istudio.distancetracker.ui.maps.presentation.state.MapStates
import com.istudio.distancetracker.ui.maps.presentation.vm.MapsVm
import com.istudio.distancetracker.utils.Constants
import com.istudio.distancetracker.utils.Constants.ACTION_SERVICE_START
import com.istudio.distancetracker.utils.Constants.ACTION_SERVICE_STOP
import com.istudio.distancetracker.utils.Constants.COUNTDOWN_TIMER_DURATION
import com.istudio.distancetracker.utils.Constants.COUNTDOWN_TIMER_INTERVAL
import com.istudio.distancetracker.utils.Constants.LOCATE_MYSELF_TIMER_DURATION
import com.istudio.distancetracker.utils.Constants.RESULT_PAGE_DISPLAY_DURATION
import com.istudio.distancetracker.utils.MapUtil.setCameraPosition
import com.istudio.distancetracker.utils.Permissions.hasBackgroundLocationPermission
import com.istudio.distancetracker.utils.Permissions.runtimeBackgroundPermission
import com.istudio.distancetracker.utils.disable
import com.istudio.distancetracker.utils.enable
import com.istudio.distancetracker.utils.hide
import com.istudio.distancetracker.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MapFragment : Fragment(), GoogleMap.OnMyLocationButtonClickListener {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var map: GoogleMap
    private lateinit var networkObserver: NetworkObserver

    private val viewModel: MapsVm by viewModels()

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
            lifecycleScope.launch {
                delay(2500)
                // Display the start button
                binding.mapMasterViewId.displayStartButton()
            }
        }
        return false
    }
    // ********************************** Over-ridden methods **************************************

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
        // Set map padding
        map.setPadding(0,0,200,0)
        // Set custom location
        setCustomIconForLocationButton()
        // Start observing the tracker service
        observeTrackerService()
    }
    // **********************************CallBacks *************************************************

    // ********************************** User defined functions ************************************
    private fun initOnCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initOnViewCreated() {
        initMapScreen()
    }

    private fun initOnDestroyView() { _binding = null }

    /**
     * We call the map to start and load asynchronously
     */
    private fun initiateMapSync() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    /**
     * Set the click listeners for all the buttons on the screen
     */
    private fun setOnClickListeners() {
        binding.mapMasterViewId.apply {
            setStartButtonClickListener { startButtonAction() }
            setStopButtonClickListener { stopButtonClicked() }
            setResetButtonClickListener { onResetButtonClicked() }
            setActLstButtonClickListener { onActivityListButtonClicked() }
            setLocationSettingsButtonClickListener {
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        }
    }

    /**
     * Observer: Here we listen to channel variable in the view-model
     */
    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collect { event ->
                when(event){
                    is MapStates.JourneyResult -> displayResults(event.result)
                    is MapStates.ShowErrorMessage -> showSnackbar(message = event.message.asString(requireContext()))
                    is MapStates.AnimateCamera -> animateCamera(event)
                    is MapStates.DisplayStartButton -> binding.mapMasterViewId.displayStartButton()
                    is MapStates.FollowCurrentLocation -> animateCameraWithDuration(event.location,event.duration)
                    is MapStates.DisableStopButton -> binding.mapMasterViewId.enableStopButton()
                    is MapStates.AnimateCameraForBiggerPitchure -> animateCameraForBiggerPitchure(event.bounds,event.padding,event.duration)
                    is MapStates.AddPolyline -> {
                        val polyline = map.addPolyline(event.polyLine)
                        viewModel.addPolylineToList(polyline)
                    }
                    is MapStates.AddMarker -> addMarker(event.location)
                }
            }
        }
    }

    /**
     * Adding the marker in the screen
     */
    private fun addMarker(position: LatLng) {
        // Adding the marker in the map
        val marker = map.addMarker(MarkerOptions().position(position))
        // Using the marker reference to store in a variable in the view model
        viewModel.addMarker(marker)
    }

    /**
     * BUTTON-ACTION: Reset the map
     */
    private fun onResetButtonClicked() { viewModel.mapReset() }

    /**
     * BUTTON-ACTION: Start button clicked
     */
    private fun startButtonAction() {
        if(hasBackgroundLocationPermission(requireContext())){
            startCountdown()
            binding.mapMasterViewId.startButtonActionUiState()
        }else{
            runtimeBackgroundPermission(this,requireActivity(),binding.root)
        }
    }

    /**
     * BUTTON-ACTION: Stop button clicked
     */
    private fun stopButtonClicked() {
        stopForegroundService()
        binding.mapMasterViewId.stoppedUiState()
    }

    private fun startCountdown() {
        binding.mapMasterViewId.countDownUiState()
        val timer: CountDownTimer = object : CountDownTimer(COUNTDOWN_TIMER_DURATION, COUNTDOWN_TIMER_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                val currentSecond = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished).toString()
                val zeroString = "0"
                if (currentSecond == zeroString) { binding.mapMasterViewId.counterGoState() }
                else { binding.mapMasterViewId.counterCountDownState(currentSecond) }
            }

            override fun onFinish() {
                binding.mapMasterViewId.hideTimerTextView()
                startLocationService()
            }
        }
        timer.start()
    }

    private fun stopForegroundService() {
        binding.mapMasterViewId.disableStartButton()
        stopLocationService()
    }

    private fun displayResults(result: CalculateResultOutput) {
        val resultCalculated = Result(result.distanceTravelled, result.elapsedTime)
        lifecycleScope.launch {
            // Give a delay for smoother transition
            delay(RESULT_PAGE_DISPLAY_DURATION)
            // Display the result page
            resultPageNavigation(resultCalculated)
            // Display the reset state for map since the result is calculated and shown
            binding.mapMasterViewId.resetMapUiState()
        }
    }

    private fun initNetworkObserver() {
        // --> Initialize the network observer in your activity or fragment
        networkObserver = NetworkObserver(requireContext(), lifecycle)
        lifecycle.addObserver(networkObserver)

        // --> Use live data to observe the network changes
        networkObserver.networkAvailableStateFlow.asLiveData().observe(viewLifecycleOwner, Observer { networkState ->
            when (networkState) {
                NetworkState.Unavailable -> SnackBarDisplay.showNetworkUnavailableAlert(binding.root)
                NetworkState.Available -> SnackBarDisplay.removeNetworkUnavailableAlert()
            }
        })
    }

    private fun initMapScreen() {
        if(!viewModel.checkLocationEnabled()){
            // GPS is not available
            binding.mapMasterViewId.showMapView(isError = true,isGpsError = true)
        }else if(!viewModel.checkConnectivity()){
            // Connectivity is not available
            binding.mapMasterViewId.showMapView(isError = true,isGpsError = false)
        }else{
            // Connectivity and GPS is available
            binding.mapMasterViewId.showMapView(isError = false)
            initiateMapSync()
            setObservers()
            initNetworkObserver()
        }
        setOnClickListeners()
    }
    // ********************************** User defined functions ************************************

    // *************************************** Location Service ************************************
    /**
     * DESCRIPTION: Observe the live actions from the location service
     */
    private fun observeTrackerService() {
        TrackerService.locationList.observe(viewLifecycleOwner) { viewModel.trackerServiceInProgress(it) }
        TrackerService.started.observe(viewLifecycleOwner) { viewModel.trackerStartedState(it) }
        TrackerService.startTime.observe(viewLifecycleOwner) { viewModel.trackerStartTime(it) }
        TrackerService.stopTime.observe(viewLifecycleOwner) { viewModel.trackerStopTime(it) }
    }

    /**
     * DESCRIPTION: Start the location service
     */
    private fun startLocationService() { sendActionCmdToService(ACTION_SERVICE_START) }

    /**
     * DESCRIPTION: Stop the location service
     */
    private fun stopLocationService() { sendActionCmdToService(ACTION_SERVICE_STOP) }

    /**
     * We shall use this to start and stop the tracker service
     */
    private fun sendActionCmdToService(action:String) {
        Intent(requireContext(), TrackerService::class.java).apply {
            this.action=action
            requireContext().startService(this)
        }
    }
    // *************************************** Location Service ************************************

    // *************************************** Navigation ******************************************
    private fun resultPageNavigation(resultCalculated: Result) {
        val directions = MapFragmentDirections.actionMapFragmentToResultFragment(resultCalculated)
        findNavController().navigate(directions)
    }

    private fun onActivityListButtonClicked() {
        //findNavController().navigate(R.id.action_mapFragment_to_distanceLogFragment)
        //locationEnabled()
    }
    // *************************************** Navigation ******************************************

    // ********************************** Animate Camera *******************************************
    private fun animateCamera(event: MapStates.AnimateCamera) {
        val newCameraPosition =  CameraUpdateFactory.newCameraPosition(setCameraPosition(event.location))
        map.animateCamera(newCameraPosition)
    }

    private fun animateCameraWithDuration(location: LatLng, duration: Int) {
        val newCameraPosition = CameraUpdateFactory.newCameraPosition(setCameraPosition(location))
        animateMap(newCameraPosition,duration)
    }

    private fun animateCameraForBiggerPitchure(bounds: LatLngBounds, padding: Int, duration: Int) {
        val newCameraPosition = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        animateMap(newCameraPosition,duration)
    }

    private fun animateMap(newCameraPosition: CameraUpdate,  duration: Int) {
        map.animateCamera(newCameraPosition,duration,null)
    }
    // ********************************** Animate Camera *******************************************

    // *************************************** States **********************************************
    private fun setCustomIconForLocationButton() {
        binding.mapMasterViewId.apply {
            setCustomIconForLocationButton()
            lifecycleScope.launch {
                // Provide a delay
                delay(Constants.LOCATE_MYSELF_TIMER_DURATION)
                initiateLocationButtonClick()
            }
        }
    }
    // *************************************** States **********************************************
}