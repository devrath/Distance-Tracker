package com.istudio.distancetracker.core.data.repository.parser

import com.istudio.distancetracker.core.domain.features.parser.ParserFeature
import com.istudio.distancetracker.core.domain.models.User

class ParserRepository(
    private val parserFeature: ParserFeature
) {

    fun convertUserObjectToJson(user: User): String {
        return parserFeature.convertUserObjectToJson(user)
    }
}
