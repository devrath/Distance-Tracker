package com.istudio.core_database.di

import com.istudio.core_database.data.implementation.DistanceTrackerConstantsDaoImpl
import com.istudio.core_database.data.repository.DistanceTrackerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideToDoDaoRepository(toDoDaoImpl: DistanceTrackerConstantsDaoImpl) : DistanceTrackerRepository {
        return DistanceTrackerRepository(toDoDaoImpl)
    }

}