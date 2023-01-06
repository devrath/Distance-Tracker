package com.demo.core_permission.di

import android.content.Context
import com.demo.core_permission.data.implementation.PermissionFeatureImpl
import com.demo.core_permission.domain.PermissionFeature
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PermissionModule {

    @Provides
    @Singleton
    fun providePermissionFeature(
        @ApplicationContext context: Context
    ): PermissionFeature {
        return PermissionFeatureImpl(context)
    }

}