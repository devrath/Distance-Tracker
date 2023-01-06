package com.demo.core_permission.di

import com.demo.core_permission.data.implementation.PermissionFeatureImpl
import com.demo.core_permission.data.repository.PermissionFeatureRepository
import com.demo.core_permission.domain.PermissionFeature
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PermissionModule {

    @Provides
    @Singleton
    fun providePermissionFeature(): PermissionFeature {
        return PermissionFeatureImpl()
    }

    @Provides
    @Singleton
    fun providePermissionRepository(implementation: PermissionFeatureImpl) : PermissionFeatureRepository {
        return PermissionFeatureRepository(implementation)
    }


}