package com.istudio.distancetracker.core.domain.features.parser

import com.istudio.distancetracker.core.domain.models.User

interface ParserFeature {
    fun convertUserObjectToJson(user: User): String
}
