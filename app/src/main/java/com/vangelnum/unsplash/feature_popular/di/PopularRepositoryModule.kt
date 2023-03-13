package com.vangelnum.unsplash.feature_popular.di

import com.vangelnum.unsplash.feature_popular.data.repository.PopularRepositoryImpl
import com.vangelnum.unsplash.feature_popular.domain.repository.PopularRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PopularRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPopularRepository(
        popularRepositoryImpl: PopularRepositoryImpl
    ): PopularRepository
}