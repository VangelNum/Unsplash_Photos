package com.vangelnum.unsplash.feature_latest.di

import com.vangelnum.unsplash.feature_latest.data.repository.MainRepositoryImpl
import com.vangelnum.unsplash.feature_latest.domain.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MainRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMainRepository(
        mainRepositoryImpl: MainRepositoryImpl
    ): MainRepository
}