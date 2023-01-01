package com.istudio.core_database.di

import com.istudio.core_database.data.implementation.ToDoDaoImpl
import com.istudio.core_database.data.repository.ToDoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideToDoDaoRepository(toDoDaoImpl: ToDoDaoImpl) : ToDoRepository {
        return ToDoRepository(toDoDaoImpl)
    }

}