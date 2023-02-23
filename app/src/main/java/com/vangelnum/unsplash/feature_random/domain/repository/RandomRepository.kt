package com.vangelnum.unsplash.feature_random.domain.repository

import com.vangelnum.unsplash.core.common.Resource
import com.vangelnum.unsplash.feature_random.domain.model.PhotosItemsRandom
import kotlinx.coroutines.flow.Flow

interface RandomRepository {
    fun getRandomPhotos(count: Int): Flow<Resource<List<PhotosItemsRandom>>>
}