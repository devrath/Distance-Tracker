package com.istudio.core_ui.di

import com.istudio.core_preferences.domain.InAppReviewPreferences
import com.istudio.core_ui.data.implementation.SwitchUiModeFeatureImpl
import com.istudio.core_ui.domain.SwitchUiModeFeature
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SwitchUiModule {

    @Provides
    @Singleton
    fun provideSwitchUiFeature(preferences: InAppReviewPreferences): SwitchUiModeFeature {
        return SwitchUiModeFeatureImpl(preferences)
    }

}