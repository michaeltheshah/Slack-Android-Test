package com.slack.exercise.di

import com.slack.exercise.api.UserSearchRepository
import com.slack.exercise.api.UserSearchService
import com.slack.exercise.dataprovider.UserSearchResultDataProvider
import com.slack.exercise.dataprovider.UserSearchResultDataProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module to setup Application scoped instances that require providers.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun provideUserSearchResultDataProvider(
        dataProvider: UserSearchResultDataProviderImpl
    ): UserSearchResultDataProvider

    @Binds
    abstract fun provideUserSearchService(apiImpl: UserSearchRepository): UserSearchService
}