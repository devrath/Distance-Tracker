package com.istudio.feat_inappreview.di

import com.istudio.feat_inappreview.manager.InAppReviewManager
import com.istudio.feat_inappreview.manager.InAppReviewManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Provides dependencies required for In App Review flow.
 * */
@Module
@InstallIn(SingletonComponent::class)
abstract class InAppReviewBinds {
    /**
     * Provides In App Review flow wrapper.
     * */
    @Binds
    @Singleton
    abstract fun bindInAppReviewManager(
        inAppReviewManagerImpl: InAppReviewManagerImpl
    ): InAppReviewManager
}