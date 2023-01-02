package com.istudio.core_database.di

import android.content.Context
import androidx.room.Room
import com.istudio.core_database.Constants.DATABASE_NAME
import com.istudio.core_database.data.implementation.ToDoDaoImpl
import com.istudio.core_database.database.ToDoDatabase
import com.istudio.core_database.domain.dao.ToDoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides a instance of RoomDatabase
     */
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ToDoDatabase::class.java,
        DATABASE_NAME
    ).build()



    @Singleton
    @Provides
    fun provideDao(database: ToDoDatabase) = database.toDoDao()

}