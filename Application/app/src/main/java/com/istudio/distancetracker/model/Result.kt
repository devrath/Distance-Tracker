package com.istudio.distancetracker.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Result(
        var distance: String,
        var time: String
): Parcelable