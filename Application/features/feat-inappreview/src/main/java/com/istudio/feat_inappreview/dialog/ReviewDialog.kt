package com.istudio.feat_inappreview.dialog


import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.istudio.core_ui.R
import com.istudio.feat_inappreview.databinding.FragmentInAppReviewPromptBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Shows a dialog that asks the user if they want to review the app.
 *
 * This dialog is shown only if the user hasn't previously rated the app, hasn't asked to never
 * rate the app or if they asked to rate it later and enough time passed (a week).
 * */
@AndroidEntryPoint
class ReviewDialog : DialogFragment() {

    var binding: FragmentInAppReviewPromptBinding? = null

    private val viewModel: ReviewDialogVm by viewModels()

    // ********************************** Life cycle methods ***************************************

    override fun getTheme() = R.style.RoundedCornersDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInAppReviewPromptBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        setObservers()
        dialog?.setCanceledOnTouchOutside(false)
    }
    // ********************************** Life cycle methods ***************************************

    // ********************************** Over-ridden methods **************************************

    /**
     * If the user cancels the dialog, we process that as if they chose to "Rate Later".
     * */
    override fun onCancel(dialog: DialogInterface) {
        //viewModel.cancelDialogAction()
        super.onCancel(dialog)
    }
    // ********************************** Over-ridden methods **************************************


    // ********************************** User defined functions ************************************
    private fun initListeners() {
        val binding = binding ?: return
        binding.apply {
            // -> ON POSITIVE ACTION
            leaveReview.setOnClickListener { viewModel.onLeaveReviewTapped(requireActivity()) }
            // -> ON NEGATIVE ACTION
            reviewLater.setOnClickListener { viewModel.rateLaterAction() }
        }
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collect { event ->
                when (event) {
                    ReviewStates.RateLaterAction -> dismissAllowingStateLoss()
                    ReviewStates.LeaveReviewAction -> dismissAllowingStateLoss()
                    ReviewStates.SendToPlayStoreScreen -> sendUserToPlayStore()
                }
            }
        }
    }

    private fun sendUserToPlayStore() {
        val cxt = context ?: return
        val appPackageName = cxt.packageName
        val markedUri = Uri.parse("market://details?id=$appPackageName")
        val playStoreUri = Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
        cxt.apply {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, markedUri))
            } catch (error: Error) {
                startActivity(Intent(Intent.ACTION_VIEW, playStoreUri))
            }
        }
    }
    // ********************************** User defined functions ************************************
}