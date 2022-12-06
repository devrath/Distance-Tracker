package com.istudio.distancetracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.istudio.distancetracker.R
import com.istudio.distancetracker.utils.Permissions.hasLocationPermission
import com.istudio.distancetracker.databinding.FragmentPermissionBinding
import com.istudio.distancetracker.utils.Permissions.runtimeLocationPermission


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
    }

    private fun initOnDestroyView() { _binding = null }

    private fun setOnClickListener() {
        binding.continueButtonId.setOnClickListener { initiateLocationFlow() }
    }

    private fun initiateLocationFlow() {
        if (hasLocationPermission(requireContext())) {
            // If the permission is available navigate to maps fragment
            navigateToMapsScreen()
        } else {
            runtimeLocationPermission(this,requireActivity(),binding.root)
        }
    }

    private fun navigateToMapsScreen() { findNavController().navigate(R.id.action_permissionFragment_to_mapFragment) }
    // ********************************** User defined functions ************************************

}