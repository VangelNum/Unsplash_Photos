package com.vangelnum.unsplash.feature_favourite.di

import com.vangelnum.unsplash.feature_favourite.data.repository.FavouriteRepositoryImpl
import com.vangelnum.unsplash.feature_favourite.domain.repository.FavouriteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavouriteRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFavouriteRepository(
        favouriteRepositoryImpl: FavouriteRepositoryImpl
    ): FavouriteRepository
}