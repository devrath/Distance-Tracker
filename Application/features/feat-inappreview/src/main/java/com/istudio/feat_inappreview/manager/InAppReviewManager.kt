package com.istudio.feat_inappreview.manager

import android.app.Activity

/**
 * Abstracts away the implementation behind the In-App Review flow.
 * */
interface InAppReviewManager {

    /**
     * Attempts to start the In-App Review flow.
     * */
    fun startReview(activity: Activity)

    /**
     * Gives the information if the user should see the review flow/prompt.
     * */
    suspend fun isEligibleForReview(): Boolean
}