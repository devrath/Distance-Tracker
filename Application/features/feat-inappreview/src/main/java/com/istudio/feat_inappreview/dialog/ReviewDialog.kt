package com.istudio.feat_inappreview.dialog


import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import android.content.DialogInterface
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.istudio.core_preferences.domain.InAppReviewPreferences
import com.istudio.feat_inappreview.R
import com.istudio.feat_inappreview.databinding.FragmentInAppReviewPromptBinding
import com.istudio.feat_inappreview.manager.InAppReviewManager
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
        viewModel.cancelDialogAction()
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
                }
            }
        }
    }
    // ********************************** User defined functions ************************************
}