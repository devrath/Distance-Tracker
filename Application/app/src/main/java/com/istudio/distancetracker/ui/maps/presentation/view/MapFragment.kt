package com.istudio.distancetracker.ui.maps.presentation.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.ButtCap
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.istudio.distancetracker.R
import com.istudio.distancetracker.core.platform.extensions.showSnackbar
import com.istudio.distancetracker.databinding.FragmentMapBinding
import com.istudio.distancetracker.features.map.domain.entities.outputs.CalculateResultOutput
import com.istudio.distancetracker.model.Result
import com.istudio.distancetracker.service.TrackerService
import com.istudio.distancetracker.ui.maps.presentation.state.MapStates
import com.istudio.distancetracker.ui.maps.presentation.vm.MapsVm
import com.istudio.distancetracker.utils.Constants.ACTION_SERVICE_START
import com.istudio.distancetracker.utils.Constants.ACTION_SERVICE_STOP
import com.istudio.distancetracker.utils.Constants.COUNTDOWN_TIMER_DURATION
import com.istudio.distancetracker.utils.Constants.COUNTDOWN_TIMER_INTERVAL
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

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

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
        val btnMyLocation: ImageView = (binding.map.findViewById<View>(Integer.parseInt("1"))?.parent as View).findViewById(Integer.parseInt("2"))
        btnMyLocation.setImageResource(R.drawable.ic_current_location)

        observeTrackerService()
    }
    // **********************************CallBacks *************************************************

    // ********************************** User defined functions ************************************
    private fun initOnCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        return binding.root
    }

    private fun initOnViewCreated() {
        initiateMapSync()
        setOnClickListeners()
        setObservers()
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collect { event ->
                when(event){
                    is MapStates.JourneyResult -> displayResults(event.result)
                    is MapStates.ShowErrorMessage -> showSnackbar(message = event.message.asString(requireContext()))
                }
            }
        }
    }

    private fun observeTrackerService() {
        TrackerService.locationList.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.locationList = it
                Log.d("LocationReceived",it.toString())
                if (viewModel.locationList.size > 1) {
                    // Giving the user the option to stop the service
                    binding.stopButton.enable()
                }
                // On each call-back, it will draw the list of points in the mutable list
                drawPolyline()
                // As when the polyline is drawn, camera has to follow the points of the mutable list
                followPolyline()
            }
        }

        TrackerService.started.observe(viewLifecycleOwner) {
            viewModel.started.value = it
        }
        TrackerService.startTime.observe(viewLifecycleOwner) {
            viewModel.startTime = it
        }
        TrackerService.stopTime.observe(viewLifecycleOwner) {
            viewModel.stopTime = it
            if (viewModel.stopTime != 0L) {
                if (viewModel.locationList.isNotEmpty()) {
                     showBiggerPicture()
                     viewModel.calculateResult()
                }
            }
        }
    }

    private fun showBiggerPicture() {
        val bounds = LatLngBounds.Builder()
        for (location in viewModel.locationList) {
            bounds.include(location)
        }
        map.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds.build(), 100
            ), 2000, null
        )
        addMarker(viewModel.locationList.first())
        addMarker(viewModel.locationList.last())
    }

    private fun addMarker(position: LatLng) {
        val marker = map.addMarker(MarkerOptions().position(position))
        marker?.let {
            viewModel.markerList.add(it)
        }
    }

    private fun drawPolyline() {
        val widthValue = 10f
        val colorValue = Color.BLUE
        val typeValue = JointType.ROUND

        val polyline = map.addPolyline(
            PolylineOptions().apply {
                width(widthValue)
                color(colorValue)
                jointType(typeValue)
                startCap(ButtCap())
                endCap(ButtCap())
                addAll(viewModel.locationList)
            }
        )
        viewModel.polylineList.add(polyline)
    }

    private fun followPolyline() {
        if (viewModel.locationList.isNotEmpty()) {
            map.animateCamera(
                (CameraUpdateFactory.newCameraPosition(
                    setCameraPosition(
                        // Lets set to the last position
                        viewModel.locationList.last()
                    )
                )), 1000, null
            )
        }
    }

    private fun setOnClickListeners() {
        binding.apply {
            startButton.setOnClickListener { startButtonAction() }
            stopButton.setOnClickListener { stopButtonClicked() }
            resetButton.setOnClickListener { onResetButtonClicked() }
            actLstId.setOnClickListener { onActivityListButtonClicked() }
        }
    }

    private fun onActivityListButtonClicked() {
        findNavController().navigate(R.id.action_mapFragment_to_distanceLogFragment)
    }

    private fun onResetButtonClicked() { mapReset() }

    private fun startButtonAction() {
        if(hasBackgroundLocationPermission(requireContext())){
            startCountdown()
            binding.startButton.disable()
            binding.startButton.hide()
            binding.stopButton.show()
        }else{
            runtimeBackgroundPermission(this,requireActivity(),binding.root)
        }
    }

    private fun stopButtonClicked() {
        stopForegroundService()
        binding.stopButton.hide()
        binding.startButton.show()
    }

    @SuppressLint("MissingPermission")
    private fun mapReset() {
        fusedLocationProviderClient.lastLocation.addOnCompleteListener {
            val lastKnownLocation = LatLng(it.result.latitude, it.result.longitude)
            map.animateCamera(
                CameraUpdateFactory.newCameraPosition(setCameraPosition(lastKnownLocation))
            )
            viewModel.resetViewModel()
            binding.apply {
                resetButton.hide()
                startButton.show()
            }
        }
    }


    private fun startCountdown() {
        binding.apply {
            timerTextView.show()
            stopButton.disable()
        }

        val timer: CountDownTimer = object : CountDownTimer(COUNTDOWN_TIMER_DURATION, COUNTDOWN_TIMER_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                val currentSecond = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished).toString()
                val zeroString = "0"
                val strGo = requireActivity().getText(R.string.go)
                val colorBlack =  ContextCompat.getColor(requireContext(), R.color.black)
                val colorRed =  ContextCompat.getColor(requireContext(), R.color.red)

                binding.apply {
                    if (currentSecond == zeroString) {
                        timerTextView.text = strGo
                        timerTextView.setTextColor(colorBlack)
                    } else {
                        timerTextView.text = currentSecond.toString()
                        timerTextView.setTextColor(colorRed)
                    }
                }
            }

            override fun onFinish() {
                binding.timerTextView.hide()
                // Start the tracker service
                sendActionCmdToService(ACTION_SERVICE_START)
            }
        }
        timer.start()
    }

    /**
     * We shall use this to start and stop the tracker service
     */
    private fun sendActionCmdToService(action:String) {
        Intent(requireContext(), TrackerService::class.java).apply {
            this.action=action
            requireContext().startService(this)
        }
    }

    private fun stopForegroundService() {
        binding.startButton.disable()
        sendActionCmdToService(ACTION_SERVICE_STOP)
    }

    private fun initiateMapSync() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun initOnDestroyView() { _binding = null }

    private fun displayResults(result: CalculateResultOutput) {
        val resultCalculated = Result(result.distanceTravelled, result.elapsedTime)
        lifecycleScope.launch {
            // Give a delay for smoother transition
            delay(2500)
            // Display the result page
            resultPageNavigation(resultCalculated)
            // Display the reset state for map since the result is calculated and shown
            resetMapUiState()
        }
    }
    // ********************************** User defined functions ************************************

    // *************************************** Navigation ******************************************
    private fun resultPageNavigation(resultCalculated: Result) {
        val directions = MapFragmentDirections.actionMapFragmentToResultFragment(resultCalculated)
        findNavController().navigate(directions)
    }
    // *************************************** Navigation ******************************************


    // *************************************** States **********************************************
    private fun resetMapUiState() {
        binding.apply {
            startButton.apply {
                hide()
                enable()
            }
            stopButton.hide()
            resetButton.show()
        }
    }
    // *************************************** States **********************************************


}