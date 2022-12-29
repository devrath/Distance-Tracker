package com.istudio.feat_inappreview.dialog

sealed class ReviewStates {
    //data class AddMarker(val location: LatLng) : MapStates()
    object RateLaterAction : ReviewStates()
    object LeaveReviewAction : ReviewStates()
    object SendToPlayStoreScreen : ReviewStates()
}
