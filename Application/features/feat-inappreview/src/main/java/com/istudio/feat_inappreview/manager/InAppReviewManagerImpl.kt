package com.istudio.feat_inappreview.manager

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.tasks.Task
import com.istudio.core_common.di.qualifiers.IoDispatcher
import com.istudio.core_preferences.data.repository.PreferenceRepository
import com.istudio.core_preferences.domain.InAppReviewPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.math.abs
import javax.inject.Inject
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

/**
 * The review manager implementation wrapper, that starts and handles the In-App Review
 * flow.
 *
 * @param reviewManager - The [ReviewManager] that handles all the internal API calls.
 * @property reviewInfo - The info for the app that enables In-App Review calls.
 * */
class InAppReviewManagerImpl  @Inject constructor(
    @ApplicationContext private val context: Context,
    private val reviewManager: ReviewManager,
    private val inAppReviewPreferences: InAppReviewPreferences,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : CoroutineScope, InAppReviewManager {

    override val coroutineContext: CoroutineContext get() = SupervisorJob() + ioDispatcher

    companion object {
        private const val KEY_REVIEW = "reviewFlow"
        private const val DAYS_FOR_REVIEW_REMINDER : Long = 14
    }

    private var reviewInfo: ReviewInfo? = null

    /**
     * After the class is created, we request the [ReviewInfo] to pre-cache it if the user is eligible.
     * The [ReviewInfo] is used to request the review flow later in the app.
     * */
    init {
        CoroutineScope(Dispatchers.IO).launch {
            if (isEligibleForReview()) {
                reviewManager.requestReviewFlow().addOnCompleteListener {
                    if (it.isComplete && it.isSuccessful) {
                        reviewInfo = it.result
                    }
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
    override suspend fun isEligibleForReview(): Boolean {
        val inAppReviewPreferencesValue = inAppReviewPreferences.hasUserChosenRateLater().first()
        val hasUserRatedApp = inAppReviewPreferences.hasUserRatedApp().first()
        return (!hasUserRatedApp && !inAppReviewPreferencesValue) || (inAppReviewPreferencesValue && enoughTimePassed())
    }

    private suspend fun enoughTimePassed(): Boolean {
        val rateLaterTimestamp = inAppReviewPreferences.getRateLaterTime().first()

        return abs(rateLaterTimestamp - System.currentTimeMillis()) >= TimeUnit.DAYS.toMillis(DAYS_FOR_REVIEW_REMINDER)
    }

    /**
     * Attempts to start review flow if the [reviewInfo] is available and the user is eligible.
     *
     * @param activity - The Activity to which the lifecycle is attached.
     * */
    override fun startReview(activity: Activity) {
        if (reviewInfo != null) {
            reviewInfo?.let {
                reviewManager.launchReviewFlow(activity, it).addOnCompleteListener { reviewFlow ->
                    onReviewFlowLaunchCompleted(reviewFlow)
                }
            }
        } else {
            sendUserToPlayStore()
        }
    }

    private fun onReviewFlowLaunchCompleted(reviewFlow: Task<Void>) {
        if (reviewFlow.isSuccessful) {
           // logSuccess()
        } else {
            sendUserToPlayStore()
        }
    }

    private fun sendUserToPlayStore() {
        val appPackageName = context.packageName

        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (error: Error) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }
}