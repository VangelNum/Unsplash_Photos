package com.vangelnum.unsplash.feature_random.data.repository

import com.vangelnum.unsplash.core.common.Resource
import com.vangelnum.unsplash.feature_random.data.api.ApiInterfaceRandom
import com.vangelnum.unsplash.feature_random.data.mappers.toNormalRandomPhotos
import com.vangelnum.unsplash.feature_random.domain.model.PhotosItemsRandom
import com.vangelnum.unsplash.feature_random.domain.repository.RandomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RandomRepositoryImpl @Inject constructor(
    private val api: ApiInterfaceRandom
) : RandomRepository {
    override fun getRandomPhotos(count: Int): Flow<Resource<List<PhotosItemsRandom>>> = flow {
        try {
            emit(Resource.Loading())
            val response = api.getRandomPhotos(count).map { dto->
                dto.toNormalRandomPhotos()
            }
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString(), data = null)
        }
    }

}