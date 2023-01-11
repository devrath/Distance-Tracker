package com.demo.core_network.di

import android.content.Context
import com.demo.core_network.api.DistanceTrackerApi
import com.demo.core_network.interceptors.AnalyticsInterceptor
import com.demo.core_network.interceptors.ApiKeyInterceptor
import com.demo.core_network.utils.NetworkUtils
import com.google.gson.Gson
import com.istudio.core_logger.data.repository.LoggerRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Timeout for the network requests
    private const val REQUEST_TIMEOUT = 3L
    private const val HTTP_INTERCEPTOR_TAG = "HttpLoggingInterceptor"

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): DistanceTrackerApi =
        retrofit.create(DistanceTrackerApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, factory: Converter.Factory): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(DistanceTrackerApi.BASE_URL)
            .addConverterFactory(factory)
            .client(client)
            .build()
        return retrofit
    }

    @Provides
    fun okHttp(
        @ApplicationContext appContext: Context,
        loggerRepo: LoggerRepository
    ): OkHttpClient {
        // val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        val logger = HttpLoggingInterceptor { message ->
            // Print the logging messages using our custom logger
            loggerRepo.d(HTTP_INTERCEPTOR_TAG, message)
        }
        logger.level = HttpLoggingInterceptor.Level.BODY
        // If something you need to remove from header of OkHttp
        logger.redactHeader("x-amz-cf-id")

        return OkHttpClient.Builder()
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(AnalyticsInterceptor(appContext))
            .addInterceptor(logger)
            .build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory():Converter.Factory{
        return NetworkUtils.buildFactoryForKotlinSerialiser()
    }

}