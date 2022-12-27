package com.istudio.distancetracker.core.data.implementation.parser

import com.google.gson.Gson
import com.istudio.distancetracker.core.domain.features.parser.ParserFeature
import com.istudio.distancetracker.core.domain.models.User

class ParserFeatureImpl(
    private val gson: Gson
) : ParserFeature {

    override fun convertUserObjectToJson(user: User): String {
        return gson.toJson(user)
    }
}
