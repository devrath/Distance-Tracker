package com.istudio.feat_inappreview

/**
 * Represents an interface all features that can prompt the In App Review Flow should implement.
 * Via this interface your app can call this and invoke the review feature
 * */
interface InAppReviewView {

    /**
     * Tells the UI to attempt to trigger the In App Review flow.
     * This doesn't guarantee the Flow will be triggered, as Google imposes hidden, mutable, quotas
     * for review flow requests.
     * */
    fun showReviewFlow()
}