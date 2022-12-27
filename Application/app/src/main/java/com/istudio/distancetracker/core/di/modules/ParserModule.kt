package com.istudio.distancetracker.core.di.modules

import com.google.gson.Gson
import com.istudio.distancetracker.core.domain.features.parser.ParserFeature
import com.istudio.distancetracker.core.data.implementation.parser.ParserFeatureImpl
import com.istudio.distancetracker.core.data.repository.parser.ParserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ParserModule {

    /**
     * STEP-3: ---> <Final Step>
     * Provides a instance of PARSER-REPOSITORY
     * *********************************************
     * We always inject a repository
     */
    @Provides
    @Singleton
    fun provideRepositoryParser(
        store: ParserFeature
    ) = ParserRepository(parserFeature = store)

    /**
     *  STEP-2: --->
     * Provides a instance of parser moshi implementation
     * *********************************************
     * We don't inject the instance of implementation directly - instead we provide it to the repository above
     */
    @Provides
    @Singleton
    fun provideParserFeature(parser: Gson): ParserFeature {
        return ParserFeatureImpl(parser)
    }

    /**
     * STEP-1: --->
     * Provides a instance of Gson
     * *********************************************
     * We don't inject the moshi instance directly - Instead we provide it to the implementation above
     */

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()
}
