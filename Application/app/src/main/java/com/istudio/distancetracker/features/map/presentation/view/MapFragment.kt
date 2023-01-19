package com.istudio.distancetracker.features.map.presentation.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.demo.core_permission.domain.PermissionFeature
import com.example.feat_gallery.presentation.view.GalleryActivity
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.istudio.core_common.extensions.SnackBarDisplay
import com.istudio.core_common.extensions.TAG
import com.istudio.core_common.extensions.showSnackbar
import com.istudio.core_common.functional.PublisherEventBus
import com.istudio.core_connectivity.service.NetworkObserver
import com.istudio.core_connectivity.service.NetworkState
import com.istudio.core_logger.domain.LoggerFeature
import com.istudio.distancetracker.Constants
import com.istudio.distancetracker.Constants.ACTION_SERVICE_START
import com.istudio.distancetracker.Constants.ACTION_SERVICE_STOP
import com.istudio.distancetracker.R
import com.istudio.distancetracker.databinding.FragmentMapBinding
import com.istudio.distancetracker.features.map.domain.entities.outputs.CalculateResultOutput
import com.istudio.distancetracker.features.map.events.EventMapStyleSelected
import com.istudio.distancetracker.features.map.presentation.state.MapStates
import com.istudio.distancetracker.features.map.presentation.vm.MapsVm
import com.istudio.distancetracker.features.map.util.FileOperations
import com.istudio.distancetracker.features.map.util.MapUtil.setCameraPosition
import com.istudio.distancetracker.model.Result
import com.istudio.distancetracker.service.TrackerService
import com.istudio.feat_inappreview.dialog.ReviewDialog
import com.istudio.feat_inappreview.manager.InAppReviewManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject


@AndroidEntryPoint
class MapFragment : Fragment(), GoogleMap.OnMyLocationButtonClickListener {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var map: GoogleMap
    private lateinit var networkObserver: NetworkObserver

    private val viewModel: MapsVm by viewModels()

    /**
     * Used to launch the In App Review flow if the user should see it.
     * */
    @Inject
    lateinit var reviewManager: InAppReviewManager

    @Inject
    lateinit var permissionFeature: PermissionFeature

    @Inject
    lateinit var log: LoggerFeature

    // ********************************** Life cycle methods ***************************************
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return initOnCreateView(inflater, container)
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
    @SuppressLint("MissingPermission", "PotentialBehaviorOverride")
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
        map.setPadding(0, 0, 20, 0)

        lifecycleScope.launchWhenStarted {
            when {
                !viewModel.isUiModeKeyStored() -> {
                    // System UI mode is not applied so use the system selection of dark/light theme
                    if(checkIfSystemHasLightMode()){ lightMode(googleMap)
                    }else{ darkMode(googleMap) }
                }
                else -> {
                    // System UI mode is not applied so use the user selection of dark/light theme
                    lifecycleScope.launchWhenStarted {
                        when {
                            viewModel.isDarkMode() -> darkMode(googleMap)
                            else -> lightMode(googleMap)
                        }
                    }
                }
            }
        }


        //map.mapType = GoogleMap.MAP_TYPE_HYBRID
        // Set custom location
        setCustomIconForLocationButton()
        // Start observing the tracker service
        observeTrackerService()
        // Initial action button set up
        initialActionButtonSetUp()
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

    private fun initOnDestroyView() {
        _binding = null
    }

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
                locationsSettingsLauncher.launch(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            setNetworkSettingsButtonClickListener {
                connectivitySettingsLauncher.launch(Intent(Settings.ACTION_SETTINGS))
            }
            setFabButtonClickListener{
                //startButtonAction()
                binding.mapMasterViewId.toggleUiMode()
            }
            setFabGalleryClickListener{
                requireActivity().apply {
                    Intent(this, GalleryActivity::class.java).apply {
                        startActivity(this)
                    }
                }
            }
            setUiModeFabButtonClickListener{
                lifecycleScope.launch {
                    // INITIATE:-> Set the UI Mode for application
                    AppCompatDelegate.setDefaultNightMode(viewModel.toggleUiMode())
                    // Save the UI Mode to preferences
                    viewModel.saveToggledUiMode()
                }
            }
            setChangeStyleButtonClickListener {
                findNavController().navigate(R.id.action_mapFragment_to_mapTypeSelectionFragment)
            }
        }
    }

    /**
     * Observer: Here we listen to channel variable in the view-model
     */
    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collect { event ->
                when (event) {
                    is MapStates.JourneyResult -> displayResults(event.result)
                    is MapStates.ShowErrorMessage -> showSnackbar(
                        message = event.message.asString(requireContext())
                    )
                    is MapStates.AnimateCamera -> animateCamera(event)
                    is MapStates.DisplayStartButton -> binding.mapMasterViewId.displayStartButton()
                    is MapStates.FollowCurrentLocation -> animateCameraWithDuration(
                        event.location, event.duration
                    )
                    is MapStates.DisableStopButton -> binding.mapMasterViewId.enableStopButton()
                    is MapStates.AnimateCameraForBiggerPitchure -> animateCameraForBiggerPicture(
                        event.bounds, event.padding, event.duration
                    )
                    is MapStates.AddPolyline -> {
                        val polyline = map.addPolyline(event.polyLine)
                        viewModel.addPolylineToList(polyline)
                    }
                    is MapStates.AddMarker -> addMarker(event.location)
                    is MapStates.LaunchInAppReview -> {
                        ReviewDialog().show(childFragmentManager, null)
                    }
                    is MapStates.CounterGoState -> binding.mapMasterViewId.counterGoState()
                    is MapStates.CounterCountDownState ->  binding.mapMasterViewId.counterCountDownState(event.currentSecond)
                    is MapStates.CounterFinishedState -> counterCountDownFinished()
                }
            }
        }
    }

    /**
     * Observe viewState in the subscriber
     */
    private fun observerEvents() {

        lifecycleScope.launch {
            PublisherEventBus.subscribe<EventMapStyleSelected> { event ->
                map.mapType = event.selection
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
        if (permissionFeature.hasBackgroundLocationPermission()) {
            startCountdown()
            binding.mapMasterViewId.startButtonActionUiState()
        } else {
            permissionFeature.runtimeBackgroundPermission(this, binding.root)
        }
    }

    /**
     * BUTTON-ACTION: Stop button clicked
     */
    private fun stopButtonClicked() {
        stopForegroundService()
        viewModel.setFlagTrackerIsUsed()
        binding.mapMasterViewId.stoppedUiState()
    }

    private fun startCountdown() {
        binding.mapMasterViewId.countDownUiState()
        viewModel.startCountdown()
    }

    private fun stopForegroundService() {
        binding.mapMasterViewId.disableStartButton()
        stopLocationService()
    }

    private fun displayResults(result: CalculateResultOutput) {
        val resultCalculated = Result(result.distanceTravelled, result.elapsedTime)
        lifecycleScope.launch {
            // Give a delay for smoother transition
            //delay(RESULT_PAGE_DISPLAY_DURATION)
            // Display the result page
            resultPageNavigation(resultCalculated)
            // Display the reset state for map since the result is calculated and shown
            binding.mapMasterViewId.resetMapUiState()
            setMapSnapshot()
        }
    }

    private fun initNetworkObserver() {
        // --> Initialize the network observer in your activity or fragment
        networkObserver = NetworkObserver(requireContext(), lifecycle)
        lifecycle.addObserver(networkObserver)

        // --> Use live data to observe the network changes
        networkObserver.networkAvailableStateFlow.asLiveData()
            .observe(viewLifecycleOwner, Observer { networkState ->
                when (networkState) {
                    NetworkState.Unavailable -> SnackBarDisplay.showNetworkUnavailableAlert(binding.root)
                    NetworkState.Available -> SnackBarDisplay.removeNetworkUnavailableAlert()
                }
            })
    }

    private fun initMapScreen() {
        if (!viewModel.checkLocationEnabled()) {
            // GPS is not available
            binding.mapMasterViewId.showMapView(isError = true, isGpsError = true)
        } else if (!viewModel.checkConnectivity()) {
            // Connectivity is not available
            binding.mapMasterViewId.showMapView(isError = true, isGpsError = false)
        } else {
            // Connectivity and GPS is available
            binding.mapMasterViewId.showMapView(isError = false)
            initiateMapSync()
            setObservers()
            observerEvents()
            initNetworkObserver()
        }
        setOnClickListeners()
    }

    private fun initialActionButtonSetUp() {
        lifecycleScope.launch {
            val isDarkMode = viewModel.isDarkMode()
            binding.mapMasterViewId.initialActionButtonSetUp(isDarkMode)
        }
    }

    /**
     * THEME-APPLIED: Light mode is applied
     */
    private fun lightMode(googleMap: GoogleMap) {
        googleMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                requireContext(), R.raw.map_light_mode
            )
        )
    }

    /**
     * THEME-APPLIED: Dark mode is applied
     */
    private fun darkMode(googleMap: GoogleMap) {
        googleMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                requireContext(), R.raw.map_dark_mode
            )
        )
    }

    /**
     * Triggered when the counter count down is finished
     */
    private fun counterCountDownFinished() {
        binding.mapMasterViewId.hideTimerTextView()
        startLocationService()
    }

    /**
     * Check if the system selected as light mode
     */
    private fun checkIfSystemHasLightMode(): Boolean {
        when (requireActivity().resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> return true
            Configuration.UI_MODE_NIGHT_YES -> return false
        }
        return true
    }
    // ********************************** User defined functions ************************************

    // *************************************** Location Service ************************************
    /**
     * DESCRIPTION: Observe the live actions from the location service
     */
    private fun observeTrackerService() {
        TrackerService.locationList.observe(viewLifecycleOwner) {
            viewModel.trackerServiceInProgress(
                it
            )
        }
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
    private fun sendActionCmdToService(action: String) {
        Intent(requireContext(), TrackerService::class.java).apply {
            this.action = action
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
        val newCameraPosition =
            CameraUpdateFactory.newCameraPosition(setCameraPosition(event.location))
        map.animateCamera(newCameraPosition)
    }

    private fun animateCameraWithDuration(location: LatLng, duration: Int) {
        val newCameraPosition = CameraUpdateFactory.newCameraPosition(setCameraPosition(location))
        animateMap(newCameraPosition, duration)
    }

    private fun animateCameraForBiggerPicture(bounds: LatLngBounds, padding: Int, duration: Int) {
        val newCameraPosition = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        animateMap(newCameraPosition, duration)
    }

    private fun animateMap(newCameraPosition: CameraUpdate, duration: Int) {
        map.animateCamera(newCameraPosition, duration, null)
    }
    // ********************************** Animate Camera *******************************************

    // ********************************** Activity Result ******************************************
    var connectivitySettingsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
            initMapScreen()
        }

    var locationsSettingsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
            initMapScreen()
        }
    // ********************************** Activity Result ******************************************

    // *************************************** States **********************************************
    private fun setCustomIconForLocationButton() {
        binding.mapMasterViewId.apply {
            lifecycleScope.launch {
                setCustomIconForLocationButton(
                    viewModel.isDarkMode(),viewModel.isUiModeKeyStored(),
                    checkIfSystemHasLightMode()
                )
                // Provide a delay
                delay(Constants.LOCATE_MYSELF_TIMER_DURATION)
                initiateLocationButtonClick()
            }
        }
    }
    // *************************************** States **********************************************

    /**
     * Getting a snapshot from the Map fragment
     */

    private fun setMapSnapshot() {
        map.snapshot { imgBitmap ->
            log.i(TAG,"Taking the snapshot for the map:-> $imgBitmap")
            val mapImage = imgBitmap?: return@snapshot
            lifecycleScope.launchWhenStarted {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    FileOperations.savePhotoToInternalStorage(requireContext(),imgBitmap)
                }
            }
        }
    }
}