package com.vangelnum.unsplash.feature_popular.di

import com.vangelnum.unsplash.feature_popular.data.api.ApiInterfacePopular
import com.vangelnum.unsplash.feature_popular.data.repository.PopularRepositoryImpl
import com.vangelnum.unsplash.feature_popular.domain.repository.PopularRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ModulePopular {
    @Provides
    @Singleton
    fun providePopularInterface(retrofit: Retrofit): ApiInterfacePopular {
        return retrofit.create(ApiInterfacePopular::class.java)
    }

    @Provides
    @Singleton
    fun providePopularRepository(api: ApiInterfacePopular): PopularRepository {
        return PopularRepositoryImpl(api)
    }

}