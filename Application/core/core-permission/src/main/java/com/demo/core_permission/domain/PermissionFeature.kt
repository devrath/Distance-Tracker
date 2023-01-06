package com.demo.core_permission.domain

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment

interface PermissionFeature {
    fun hasLocationPermission(context: Context): Boolean
    fun runtimeLocationPermission(fragment: Fragment, context: Context, view: View)
    fun isBackgroundPermissionRequired(): Boolean
    fun hasBackgroundLocationPermission(context: Context): Boolean
    fun runtimeBackgroundPermission(fragment: Fragment, context: Context, view: View)
}
