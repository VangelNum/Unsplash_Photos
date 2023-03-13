package com.vangelnum.unsplash.feature_random.di

import com.vangelnum.unsplash.feature_random.data.repository.RandomRepositoryImpl
import com.vangelnum.unsplash.feature_random.domain.repository.RandomRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RandomRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRandomRepository(
        randomRepositoryImpl: RandomRepositoryImpl
    ): RandomRepository
}