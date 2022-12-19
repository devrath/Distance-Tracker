package com.istudio.distancetracker.core.di.modules

import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.istudio.distancetracker.core.data.implementation.connectivity.ConnectivityFeatureImpl
import com.istudio.distancetracker.core.data.implementation.location.LastLocationFeatureImpl
import com.istudio.distancetracker.core.data.implementation.location.LocationFeatureImpl
import com.istudio.distancetracker.core.domain.features.connectivity.ConnectivityFeature
import com.istudio.distancetracker.core.domain.features.location.LastLocationFeature
import com.istudio.distancetracker.core.domain.features.location.LocationFeature
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideGpsCheckFeature(locationFeature: LocationFeature) = locationFeature.isLocationEnabled()

    @Provides
    @Singleton
    fun provideGpsCheckImplementation(locationManager: LocationManager): LocationFeature {
        return LocationFeatureImpl(locationManager)
    }

    @Singleton
    @Provides
    suspend fun provideLastKnownLocationFeature(lastLocationFeature: LastLocationFeature): Flow<Location> {
        return lastLocationFeature.currentLocation()
    }

    @Provides
    @Singleton
    fun provideLastKnownLocationImplementation(fusedLocationProviderClient: FusedLocationProviderClient): LastLocationFeature {
        return LastLocationFeatureImpl(fusedLocationProviderClient)
    }

    @Singleton
    @Provides
    fun provideFusedLocationService(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Singleton
    @Provides
    fun provideLocationManager(@ApplicationContext context: Context): LocationManager {
        return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

}