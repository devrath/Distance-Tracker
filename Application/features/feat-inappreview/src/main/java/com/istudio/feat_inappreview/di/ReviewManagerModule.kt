package com.istudio.feat_inappreview.di

import android.content.Context
import com.google.android.play.core.review.ReviewManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * How module works: Instance creation happens from the bottom to the top
 * This Module provides the instance of repository to be used
 */
@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    @Provides
    @Singleton
    fun provideReviewManager(
        @ApplicationContext context: Context
    ) : ReviewManager {


    }

}
