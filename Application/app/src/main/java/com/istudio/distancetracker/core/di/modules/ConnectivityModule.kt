package com.istudio.distancetracker.core.di.modules

import android.content.Context
import com.istudio.distancetracker.core.data.implementation.connectivity.ConnectivityFeatureImpl
import com.istudio.distancetracker.core.domain.features.connectivity.ConnectivityFeature
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
    fun provideRepositoryLogger(connectivityFeature: ConnectivityFeature) = connectivityFeature.checkConnectivity()

    @Provides
    @Singleton
    fun provideLoggerFeature(@ApplicationContext appContext: Context): ConnectivityFeature {
        return ConnectivityFeatureImpl(appContext)
    }

}