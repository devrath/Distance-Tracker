package com.istudio.distancetracker.core.domain.features.firebase

import com.google.firebase.firestore.FirebaseFirestore

interface FirebaseFirestoreFeature {
    fun getFirebaseFirestore(): FirebaseFirestore
}
