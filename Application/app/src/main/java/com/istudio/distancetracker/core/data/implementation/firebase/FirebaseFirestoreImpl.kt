package com.istudio.distancetracker.core.data.implementation.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.istudio.distancetracker.core.domain.features.firebase.FirebaseFirestoreFeature

class FirebaseFirestoreImpl(
    private val firebaseFirestore: FirebaseFirestore
) : FirebaseFirestoreFeature {

    override fun getFirebaseFirestore(): FirebaseFirestore { return firebaseFirestore }
}
