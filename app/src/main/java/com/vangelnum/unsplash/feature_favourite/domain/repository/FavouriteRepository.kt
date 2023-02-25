package com.vangelnum.unsplash.feature_favourite.domain.repository

import com.vangelnum.unsplash.core.common.Resource
import com.vangelnum.unsplash.feature_favourite.domain.model.FavouriteItem
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {
    fun getAllPhotos(): Flow<Resource<List<FavouriteItem>>>
    suspend fun addPhoto(photo: FavouriteItem)
    suspend fun deletePhoto(photo: FavouriteItem)
}