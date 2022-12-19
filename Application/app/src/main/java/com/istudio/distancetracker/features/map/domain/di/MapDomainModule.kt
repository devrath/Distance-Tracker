package com.istudio.distancetracker.features.map.domain.di

import com.istudio.distancetracker.core.domain.features.logger.LoggerFeature
import com.istudio.distancetracker.features.map.domain.MapFragmentUseCases
import com.istudio.distancetracker.features.map.domain.usecases.CalculateResultUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MapDomainModule {

    @ViewModelScoped
    @Provides
    fun provideTrackerUseCases(logger: LoggerFeature ): MapFragmentUseCases {
        return MapFragmentUseCases(calculateResult = CalculateResultUseCase(log = logger))
    }

}
