package com.istudio.distancetracker.core.data.implementation.firebase

import com.google.firebase.auth.FirebaseAuth
import com.istudio.distancetracker.core.domain.features.firebase.FirebaseAuthFeature

class FirebaseAuthImpl(
    private val firebaseAuth: FirebaseAuth
) : FirebaseAuthFeature {

    override fun getFirebaseAuth(): FirebaseAuth { return firebaseAuth }
}
