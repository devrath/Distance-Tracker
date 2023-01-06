package com.demo.core_permission.data.repository

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.demo.core_permission.data.implementation.PermissionFeatureImpl
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

open class PermissionFeatureRepository @Inject constructor(
    private val feature: PermissionFeatureImpl
) {

    fun hasLocationPermission(context: Context): Boolean {
        return feature.hasLocationPermission(context)
    }

    fun runtimeLocationPermission(fragment: Fragment, context: Context, view: View) {
        feature.runtimeLocationPermission(fragment,context,view)
    }

    fun isBackgroundPermissionRequired(): Boolean {
        return feature.isBackgroundPermissionRequired()
    }

    fun hasBackgroundLocationPermission(context: Context): Boolean {
        return feature.hasBackgroundLocationPermission(context)
    }

    fun runtimeBackgroundPermission(fragment: Fragment, context: Context, view: View) {
        return feature.runtimeBackgroundPermission(fragment,context,view)
    }

}
