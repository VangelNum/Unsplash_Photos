package com.vangelnum.unsplash.feature_favourite.di

import android.content.Context
import androidx.room.Room
import com.vangelnum.unsplash.feature_favourite.data.network.FavouriteDao
import com.vangelnum.unsplash.feature_favourite.data.network.FavouriteDatabase
import com.vangelnum.unsplash.feature_favourite.data.repository.FavouriteRepositoryImpl
import com.vangelnum.unsplash.feature_favourite.domain.repository.FavouriteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavouriteModule {

    @Singleton
    @Provides
    fun provideFavouriteDatabase(@ApplicationContext context: Context): FavouriteDatabase {
        synchronized(this) {
            return Room.databaseBuilder(
                context,
                FavouriteDatabase::class.java,
                "favourite_dataBase"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    @Singleton
    @Provides
    fun provideFavouritePhotosDao(appDatabase: FavouriteDatabase): FavouriteDao {
        return appDatabase.getDao()
    }


    @Singleton
    @Provides
    fun provideRepository(dao: FavouriteDao): FavouriteRepository {
        return FavouriteRepositoryImpl(dao)
    }
}