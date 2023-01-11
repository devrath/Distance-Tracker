package com.demo.core_network.di

import com.demo.core_network.api.DistanceTrackerApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): DistanceTrackerApi =
        retrofit.create(DistanceTrackerApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(client : OkHttpClient,gson: Gson, factory : GsonConverterFactory): Retrofit =
        Retrofit.Builder()
            .baseUrl(DistanceTrackerApi.BASE_URL)
            .addConverterFactory(factory)
            .client(client)
            .build()

    @Provides
    fun okHttp(): OkHttpClient {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideSerialiser() : Gson {
        return Gson()
    }
}