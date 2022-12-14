package com.istudio.distancetracker.core.data.implementation.parser

import com.istudio.distancetracker.core.domain.features.parser.ParserFeature
import com.istudio.distancetracker.core.domain.models.User
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class ParserFeatureImpl(
    private val moshi: Moshi
) : ParserFeature {

    override fun convertUserObjectToJson(user: User): String {
        val jsonAdapter: JsonAdapter<User> = moshi.adapter(User::class.java)
        jsonAdapter.toJson(user)?.let { return it } ?: run { return "" }
    }
}
