package com.demo.core_permission.data.repository

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.demo.core_permission.data.implementation.PermissionFeatureImpl
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

open class PermissionFeatureRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val feature: PermissionFeatureImpl
) {

    fun hasLocationPermission(): Boolean {
        return feature.hasLocationPermission()
    }

    fun runtimeLocationPermission(fragment: Fragment, view: View) {
        feature.runtimeLocationPermission(fragment,view)
    }

    fun isBackgroundPermissionRequired(): Boolean {
        return feature.isBackgroundPermissionRequired()
    }

    fun hasBackgroundLocationPermission(): Boolean {
        return feature.hasBackgroundLocationPermission()
    }

    fun runtimeBackgroundPermission(fragment: Fragment, view: View) {
        return feature.runtimeBackgroundPermission(fragment,view)
    }


}
