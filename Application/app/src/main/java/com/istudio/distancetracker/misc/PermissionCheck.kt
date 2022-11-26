package com.istudio.distancetracker.misc

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionCheck {

//    private fun checkLocationPermission() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//            == PackageManager.PERMISSION_GRANTED
//        ) {
//            map.isMyLocationEnabled = true
//            Toast.makeText(this, "Already Enabled", Toast.LENGTH_SHORT).show()
//        } else {
//            requestPermission()
//        }
//    }
//
//    private fun requestPermission() {
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//            1
//        )
//    }
//
//    @SuppressLint("MissingPermission")
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        if (requestCode != 1) {
//            return
//        }
//        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(this, "Granted!", Toast.LENGTH_SHORT).show()
//            map.isMyLocationEnabled = true
//        } else {
//            Toast.makeText(this, "We need your permission!", Toast.LENGTH_SHORT).show()
//        }
//    }

}