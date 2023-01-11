package com.demo.core_network.interceptors

import android.content.Context
import android.os.Build
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AnalyticsInterceptor(private val context: Context): Interceptor {

    private val APP_VERSION = "X-App-Version"
    private val DEVICE_MODEL = "X-Device-Model"
    private val DEVICE_PLATFORM = "X-Device-Platform"
    private val OS_VERSION = "X-Device-OS-Version"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        val requestBuilder: Request.Builder = request.newBuilder()

        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        val version = packageInfo.versionName

        requestBuilder.addHeader(APP_VERSION, version)
        requestBuilder.addHeader(OS_VERSION, Build.VERSION.SDK_INT.toString())
        requestBuilder.addHeader(DEVICE_MODEL, Build.MODEL)
        requestBuilder.addHeader(DEVICE_PLATFORM, "android")

        return chain.proceed(requestBuilder.build())
    }
}