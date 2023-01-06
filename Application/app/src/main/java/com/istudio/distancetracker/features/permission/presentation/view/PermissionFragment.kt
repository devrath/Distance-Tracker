package com.istudio.distancetracker.features.permission.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.demo.core_permission.domain.PermissionFeature
import com.istudio.distancetracker.R
import com.istudio.distancetracker.databinding.FragmentPermissionBinding
import com.istudio.distancetracker.features.permission.presentation.state.PermissionStates
import com.istudio.distancetracker.features.permission.presentation.vm.PermissionVm
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PermissionFragment : Fragment(){

    private var _binding: FragmentPermissionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PermissionVm by viewModels()

    @Inject lateinit var permissionFeature: PermissionFeature


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
        setObservers()
    }

    private fun initOnDestroyView() {
        _binding = null
    }

    /**
     * Observer: Here we listen to channel variable in the view-model
     */
    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.events.collect { event ->
                when (event) {
                    PermissionStates.NavigateToMapsScreen -> findNavController().navigate(R.id.action_permissionFragment_to_mapFragment)
                    PermissionStates.RuntimeLocationPermission -> permissionFeature.runtimeLocationPermission(this@PermissionFragment,binding.root)
                }
            }
        }
    }

    private fun setOnClickListener() {
        binding.continueButtonId.setOnClickListener { viewModel.initiateLocationFlow() }
    }
    // ********************************** User defined functions ************************************

}