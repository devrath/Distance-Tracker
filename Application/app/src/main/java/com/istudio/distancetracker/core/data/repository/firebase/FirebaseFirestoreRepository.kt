package com.istudio.distancetracker.core.data.repository.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.istudio.distancetracker.core.domain.features.firebase.FirebaseFirestoreFeature

class FirebaseFirestoreRepository(
    private val fireStore: FirebaseFirestoreFeature
) {

    fun getFirebaseFirestore(): FirebaseFirestore {
        return fireStore.getFirebaseFirestore()
    }
}
