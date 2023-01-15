package com.istudio.core_common.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * What it does: Starts a activity
 * Source: https://stackoverflow.com/a/57925521/1083093
 */
inline fun <reified T : Activity> Context.startActivity(block: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply(block))
}