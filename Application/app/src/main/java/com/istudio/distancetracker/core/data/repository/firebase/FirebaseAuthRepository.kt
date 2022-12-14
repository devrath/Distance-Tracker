package com.istudio.distancetracker.core.data.repository.firebase

import com.google.firebase.auth.FirebaseAuth
import com.istudio.distancetracker.core.domain.features.firebase.FirebaseAuthFeature

class FirebaseAuthRepository(
    private val authFeature: FirebaseAuthFeature
) {

    fun getFirebaseAuth(): FirebaseAuth {
        return authFeature.getFirebaseAuth()
    }
}
