package com.demo.core_network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor: Interceptor {

    private val apiKeyQueryParameterKey = "api_key"

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val url = originalUrl.newBuilder()
            .addQueryParameter(apiKeyQueryParameterKey, "SomeToken")
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(url)
            .build()

        return chain.proceed(newRequest)
    }
}