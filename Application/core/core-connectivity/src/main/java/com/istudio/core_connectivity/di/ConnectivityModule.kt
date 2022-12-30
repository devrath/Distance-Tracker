package com.istudio.core_connectivity.di

import android.content.Context
import com.istudio.core_connectivity.data.implementation.ConnectivityFeatureImpl
import com.istudio.core_connectivity.domain.ConnectivityFeature
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConnectivityModule {

    @Provides
    @Singleton
    fun provideConnectivityFeature(connectivityFeature: ConnectivityFeature) = connectivityFeature.checkConnectivity()

    @Provides
    @Singleton
    fun provideConnectivityImplementation(@ApplicationContext appContext: Context): ConnectivityFeature {
        return ConnectivityFeatureImpl(appContext)
    }
}
