package com.demo.core_permission.data.implementation

import android.Manifest
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import com.demo.core_permission.domain.PermissionFeature
import com.google.android.material.snackbar.Snackbar
import com.istudio.core_common.extensions.openAppNotificationSettings
import com.istudio.core_ui.R
import com.permissionx.guolindev.PermissionX
import javax.inject.Inject

class PermissionFeatureImpl @Inject constructor() : PermissionFeature {

    @RequiresApi(Build.VERSION_CODES.Q)
    val permissionBackgroundLocation = Manifest.permission.ACCESS_BACKGROUND_LOCATION
    private val permissionLocation = Manifest.permission.ACCESS_FINE_LOCATION
    private val granted = PermissionChecker.PERMISSION_GRANTED


    // <-------------------------------- Location Permission -------------------------------------->
    override fun hasLocationPermission(context: Context) = ContextCompat.checkSelfPermission(context, permissionLocation) == granted

    override fun runtimeLocationPermission(fragment: Fragment, context: Context, view: View){
        PermissionX.init(fragment)
            .permissions(android.Manifest.permission.ACCESS_FINE_LOCATION)
            .setDialogTintColor(Color.parseColor("#1972e8"), Color.parseColor("#8ab6f5"))
            .onExplainRequestReason { scope, deniedList, beforeRequest ->
                val message = context.getText(R.string.str_provide_permissions).toString()
                scope.showRequestReasonDialog(
                    deniedList, message,
                    context.getText(R.string.str_allow).toString(),
                    context.getText(R.string.str_deny).toString())

            }
            .request { allGranted, grantedList, deniedList ->
                if (!allGranted) {
                    Snackbar.make(view, context.getText(R.string.str_location_permission_required), Snackbar.LENGTH_LONG)
                        .setAction(context.getText(R.string.str_location)) { context.openAppNotificationSettings() }
                        .show()
                }
            }
    }
    // <-------------------------------- Location Permission -------------------------------------->


    // <-------------------------------- BackGround Permission ------------------------------------>
    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
    override fun isBackgroundPermissionRequired(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    override fun hasBackgroundLocationPermission(context: Context): Boolean {
        return if(isBackgroundPermissionRequired()){
            // The permission ACCESS_BACKGROUND_LOCATION is needed from android-10 and above, So check it
            ContextCompat.checkSelfPermission(context, permissionBackgroundLocation) == granted
        }else{
            // In older version its by default provided
            true
        }
    }

    override fun runtimeBackgroundPermission(fragment: Fragment, context: Context, view: View){
        if(isBackgroundPermissionRequired()){
            PermissionX.init(fragment)
                .permissions(permissionBackgroundLocation)
                .setDialogTintColor(Color.parseColor("#1972e8"), Color.parseColor("#8ab6f5"))
                .onExplainRequestReason { scope, deniedList, beforeRequest ->
                    val message = context.getText(R.string.str_provide_permissions).toString()
                    scope.showRequestReasonDialog(
                        deniedList, message,
                        context.getText(R.string.str_allow).toString(),
                        context.getText(R.string.str_deny).toString())
                }
                .request { allGranted, grantedList, deniedList ->
                    if (!allGranted) {
                        Snackbar.make(view, context.getText(R.string.str_location_permission_required), Snackbar.LENGTH_LONG)
                            .setAction(context.getText(R.string.str_location)) {
                                context.openAppNotificationSettings()
                            }
                            .show()
                    }
                }
        }
    }
    // <-------------------------------- BackGround Permission ------------------------------------>



}
