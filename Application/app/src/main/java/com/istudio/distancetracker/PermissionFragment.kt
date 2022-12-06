package com.istudio.distancetracker

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.istudio.distancetracker.Permissions.hasLocationPermission
import com.istudio.distancetracker.Permissions.permissionLocation
import com.istudio.distancetracker.databinding.FragmentPermissionBinding
import com.istudio.distancetracker.utils.openAppNotificationSettings
import com.permissionx.guolindev.PermissionX


class PermissionFragment : Fragment(){

    private var _binding: FragmentPermissionBinding? = null
    private val binding get() = _binding!!

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

    // ********************************** User defined functions ************************************
    private fun initOnCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentPermissionBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initOnViewCreated() {
        setOnClickListener()
        secondTimeLogin()
    }

    private fun initOnDestroyView() { _binding = null }

    private fun setOnClickListener() {
        binding.continueButtonId.setOnClickListener { initiateLocationFlow() }
    }

    private fun secondTimeLogin() {
        // If the permission is available navigate to maps fragment
        if (hasLocationPermission(requireContext())) { navigateToMapsScreen() }
    }

    private fun initiateLocationFlow() {
        if (hasLocationPermission(requireContext())) {
            // If the permission is available navigate to maps fragment
            navigateToMapsScreen()
        } else {
            requestPermission()
        }
    }

    private fun navigateToMapsScreen() { findNavController().navigate(R.id.action_permissionFragment_to_mapFragment) }

    private fun requestPermission(){

        PermissionX.init(this)
            .permissions(android.Manifest.permission.ACCESS_FINE_LOCATION)
            .setDialogTintColor(Color.parseColor("#1972e8"), Color.parseColor("#8ab6f5"))
            .onExplainRequestReason { scope, deniedList, beforeRequest ->
                val message = requireActivity().getText(R.string.str_provide_permissions).toString()
                scope.showRequestReasonDialog(
                    deniedList, message,
                    requireActivity().getText(R.string.str_allow).toString(),
                    requireActivity().getText(R.string.str_deny).toString())

            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) { navigateToMapsScreen() }
                else {
                    Snackbar.make(binding.root, requireActivity().getText(R.string.str_location_permission_required), Snackbar.LENGTH_LONG)
                        .setAction(requireActivity().getText(R.string.str_location)) { requireActivity().openAppNotificationSettings() }
                        .show()
                }
            }
    }
    // ********************************** User defined functions ************************************

}