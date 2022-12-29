package com.istudio.feat_inappreview.manager

import android.app.Activity
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.istudio.core_common.di.qualifiers.IoDispatcher
import com.istudio.core_preferences.domain.InAppReviewPreferences
import com.istudio.feat_inappreview.ReviewFeatureConstants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.CancellationException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.math.abs

/**
 * The review manager implementation wrapper, that starts and handles the In-App Review
 * flow.
 *
 * @param reviewManager - The [ReviewManager] that handles all the internal API calls.
 * @property reviewInfo - The info for the app that enables In-App Review calls.
 * */
class InAppReviewManagerImpl  @Inject constructor(
    private val reviewManager: ReviewManager,
    private val inAppReviewPreferences: InAppReviewPreferences,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : CoroutineScope, InAppReviewManager {

    private var reviewInfo: ReviewInfo? = null

    /**
     * Exception Handler
     * */
    override val coroutineContext: CoroutineContext get() = coroutineContextHandling()

    /**
     * After the class is created, we request the [ReviewInfo] to pre-cache it if the user is eligible.
     * The [ReviewInfo] is used to request the review flow later in the app.
     * */
    init {
        CoroutineScope(coroutineContext).launch {
            val isEligibleForReview = withContext(dispatcher){ isEligibleForReview() }

            if (isEligibleForReview) {
                reviewManager.requestReviewFlow().addOnCompleteListener {
                    if (it.isComplete && it.isSuccessful) {
                        reviewInfo = it.result
                    }
                }.addOnFailureListener {

                }
            }
        }
    }

    /**
     * Returns if the user is eligible for a review.
     *
     * They are eligible only if they haven't rated before and they haven't chosen the "never" option,
     * or if they asked to rate later and a week has passed.
     * */
    override suspend fun isEligibleForReview(): Boolean  {
        // Has user chosen rate later
        val inAppReviewPreferencesValue = inAppReviewPreferences.hasUserChosenRateLater().first()
        // Has the user rated the app
        val hasUserRatedApp = inAppReviewPreferences.hasUserRatedApp().first()
        // Rate later time stamp from the time when user clicked cancel
        val rateLaterTimestamp = inAppReviewPreferences.getRateLaterTime().first()
        // Get the number of times user has rated the app
        val noOfDistanceTracked = inAppReviewPreferences.noOfDistanceTracked().first()
        // Has enough time has passed after clicking cancel
        val enoughTimePassed  =  abs(rateLaterTimestamp - System.currentTimeMillis()) >= TimeUnit.DAYS.toMillis(
            ReviewFeatureConstants.DAYS_FOR_REVIEW_REMINDER
        )

        val firstSetOfValues = (!hasUserRatedApp && !inAppReviewPreferencesValue) || (inAppReviewPreferencesValue && enoughTimePassed)
        val secondSetOfValues = isTimesRatedValidForReview(noOfDistanceTracked)
        return firstSetOfValues && secondSetOfValues
    }

    /**
     * If user has used the feature at least more than or equal to three times
     */
    private fun isTimesRatedValidForReview(noOfDistanceTracked: Int) = noOfDistanceTracked>=3

    /**
     * Attempts to start review flow if the [reviewInfo] is available and the user is eligible.
     *
     * @param activity - The Activity to which the lifecycle is attached.
     * */
    override suspend fun startReview(activity: Activity): Boolean {
        return if (reviewInfo != null) {
            val reviewDeferred = withContext(coroutineContext){
                async { reviewManager.launchReviewFlow(activity, reviewInfo!!) }
            }.await()
            reviewDeferred.isSuccessful
        } else {
            false
        }
    }

    /**
     * Cancels the coroutines if any are under the process
     * */
    override fun cancelCoroutines(message: String) {
        when {
            coroutineContext.isActive -> {
                coroutineContext.cancel(CancellationException(message))
            }
        }
    }


    private fun coroutineContextHandling() =
        SupervisorJob() + dispatcher + CoroutineExceptionHandler { _, e ->
            println(
                "exception handler: $e"
            )
        }
}