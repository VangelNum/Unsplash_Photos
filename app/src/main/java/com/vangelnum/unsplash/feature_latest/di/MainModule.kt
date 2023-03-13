package com.vangelnum.unsplash.feature_latest.di

import com.vangelnum.unsplash.feature_latest.data.api.ApiInterfaceMain
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    @Singleton
    fun provideApiInterfaceMain(retrofit: Retrofit): ApiInterfaceMain {
        return retrofit.create(ApiInterfaceMain::class.java)
    }

}