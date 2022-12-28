package com.istudio.feat_inappreview.dialog

import android.app.Activity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.istudio.core_common.base.BaseViewModel
import com.istudio.core_preferences.domain.InAppReviewPreferences
import com.istudio.feat_inappreview.ReviewFeatureConstants
import com.istudio.feat_inappreview.manager.InAppReviewManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class ReviewDialogVm @Inject constructor(
    // Preferences used to update the rate app prompt flags.
    var preferences: InAppReviewPreferences,
    // Manager used to trigger the In App Review prompt if needed.
    var inAppReviewManager: InAppReviewManager
) : BaseViewModel() {

    private val _eventChannel = Channel<ReviewStates>()
    val events = _eventChannel.receiveAsFlow()

    /**
     * ON POSITIVE ACTION
     *
     * Start the review
     * Set user rated the app flag => true
     */
    fun onLeaveReviewTapped(context : Activity) {
        viewModelScope.launch { preferences.setUserRatedApp(true) }
        inAppReviewManager.startReview(context)
        viewModelScope.launch { _eventChannel.send(ReviewStates.LeaveReviewAction) }
    }

    /**
     * ON NEGATIVE ACTION
     *
     * Set user chosen the app flag => true
     * Set the later time stamp
     */
    fun rateLaterAction() {
        cancelDialogAction()
        viewModelScope.launch { _eventChannel.send(ReviewStates.RateLaterAction) }
    }

    fun cancelDialogAction() {
        viewModelScope.launch {
            preferences.apply {
                setUserChosenRateLater(true)
                setRateLater(getLaterTime())
            }
        }
    }

    private fun getLaterTime(): Long {
        return System.currentTimeMillis() + TimeUnit.DAYS.toMillis(ReviewFeatureConstants.DAYS_FOR_REVIEW_REMINDER)
    }

}