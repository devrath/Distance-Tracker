package com.demo.core_permission.domain

import android.view.View
import androidx.fragment.app.Fragment

interface PermissionFeature {
    fun hasLocationPermission(): Boolean
    fun runtimeLocationPermission(fragment: Fragment, view: View)
    fun isBackgroundPermissionRequired(): Boolean
    fun hasBackgroundLocationPermission(): Boolean
    fun runtimeBackgroundPermission(fragment: Fragment, view: View)
}
