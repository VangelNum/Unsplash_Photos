package com.vangelnum.unsplash.feature_search.di

import com.vangelnum.unsplash.feature_search.data.api.ApiInterfaceSearch
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ModuleSearch {
    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ApiInterfaceSearch {
        return retrofit.create(ApiInterfaceSearch::class.java)
    }
}