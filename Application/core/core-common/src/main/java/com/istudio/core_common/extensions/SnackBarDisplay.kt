package com.istudio.core_common.extensions

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.istudio.distancetracker.R
import kotlin.concurrent.timer

object SnackBarDisplay {

    private var snackbar: Snackbar? = null

    fun showNetworkUnavailableAlert(view : View) {
        snackbar = Snackbar.make(view, R.string.network_is_unavailable, Snackbar.LENGTH_INDEFINITE)
            .setActionTextColor(ContextCompat.getColor(view.context, R.color.white))
        snackbar?.let {
            val snackBarView: View = it.view
            snackBarView.setBackgroundColor(ContextCompat.getColor(view.context, R.color.colorRed))
        }
        snackbar?.show()
    }

    fun removeNetworkUnavailableAlert() {
        snackbar?.let {
            val view: View = it.view
            val snackBarText = view.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
            snackBarText.text = view.context.resources.getText(R.string.network_is_available)
            view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.colorGreen))
            timer(initialDelay = 1000L, period = 1000L ) { it.dismiss() }
        }
    }

}