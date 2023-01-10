package com.istudio.core_database.di

import com.istudio.core_database.data.implementation.DistanceTrackerConstantsDaoImpl
import com.istudio.core_database.data.repository.DistanceTrackerDbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideToDoDaoRepository(toDoDaoImpl: DistanceTrackerConstantsDaoImpl) : DistanceTrackerDbRepository {
        return DistanceTrackerDbRepository(toDoDaoImpl)
    }

}