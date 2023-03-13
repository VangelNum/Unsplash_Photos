package com.vangelnum.unsplash.feature_random.di

import com.vangelnum.unsplash.feature_random.data.api.ApiInterfaceRandom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RandomModule {

    @Provides
    @Singleton
    fun provideApiRandom(retrofit: Retrofit): ApiInterfaceRandom {
        return retrofit.create(ApiInterfaceRandom::class.java)
    }


}