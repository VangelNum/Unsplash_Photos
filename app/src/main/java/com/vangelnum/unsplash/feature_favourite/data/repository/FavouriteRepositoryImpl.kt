package com.vangelnum.unsplash.feature_favourite.data.repository

import com.vangelnum.unsplash.core.common.Resource
import com.vangelnum.unsplash.feature_favourite.data.network.FavouriteDao
import com.vangelnum.unsplash.feature_favourite.domain.model.FavouriteItem
import com.vangelnum.unsplash.feature_favourite.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val dao: FavouriteDao
) : FavouriteRepository {
    override fun getAllPhotos(): Flow<Resource<List<FavouriteItem>>> = flow {
        emit(Resource.Loading())
        try {
            val response = dao.getAll()
            response.collect {
                emit(Resource.Success(it))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message.toString()))
        }
    }

    override suspend fun addPhoto(photo: FavouriteItem) {
        dao.addPhoto(photo)
    }

    override suspend fun deletePhoto(photo: FavouriteItem) {
        dao.deletePhoto(photo)
    }
}