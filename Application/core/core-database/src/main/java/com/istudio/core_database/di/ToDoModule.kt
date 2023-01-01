package com.istudio.core_database.di

import com.istudio.core_database.data.implementation.ToDoDaoImpl
import com.istudio.core_database.data.repository.ToDoRepository
import com.istudio.core_database.database.ToDoDatabase
import com.istudio.core_database.domain.dao.ToDoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ToDoModule {

    @Singleton
    @Provides
    fun provideToDoDaoRepository(toDoDaoImpl: ToDoDaoImpl) : ToDoRepository{
        return ToDoRepository(toDoDaoImpl)
    }


    @Singleton
    @Provides
    fun provideToDoDaoImpl(toDoDao: ToDoDao) : ToDoDao{
        return ToDoDaoImpl(toDoDao)
    }


    @Singleton
    @Provides
    fun provideDao(database: ToDoDatabase) = database.toDoDao()


}