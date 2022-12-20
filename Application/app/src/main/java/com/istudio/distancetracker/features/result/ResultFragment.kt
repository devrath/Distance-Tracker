package com.istudio.distancetracker.features.result

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.istudio.distancetracker.databinding.FragmentResultBinding

class ResultFragment : BottomSheetDialogFragment() {

    private val args: ResultFragmentArgs by navArgs()

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    var distance : String = ""
    var time : String = ""

    // ********************************** Life cycle methods ***************************************
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
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
        getDataFromPreviousScreen()
        setOnClickListener()
        setDataForTheScreen()
    }

    private fun initOnDestroyView() { _binding = null }


    /**
     * Set on click listeners for the screen
     */
    private fun setOnClickListener() {
        binding.apply {
            closeId.setOnClickListener { dismiss() }
            shareButton.setOnClickListener { shareResult() }
        }
    }

    /**
     * Getting the data from the previous screen
     */
    private fun getDataFromPreviousScreen() {
        distance = args.result.distance
        time = args.result.time
    }

    /**
     * Set the data for the current screen
     */
    private fun setDataForTheScreen() {
        binding.apply {
            distanceValueTextView.text = distance
            timeValueTextView.text = time
        }
    }

    /**
     * Open the share sheet for sharing the result
     */
    private fun shareResult() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "I went ${args.result.distance}km in ${args.result.time}!")
        }
        startActivity(shareIntent)
    }
    // ********************************** User defined functions ************************************

}