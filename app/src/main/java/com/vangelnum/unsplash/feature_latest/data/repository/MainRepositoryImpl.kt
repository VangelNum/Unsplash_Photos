package com.vangelnum.unsplash.feature_latest.data.repository

import com.vangelnum.unsplash.feature_latest.data.api.ApiInterfaceMain
import com.vangelnum.unsplash.feature_latest.data.mapper.toNormalPhotos
import com.vangelnum.unsplash.feature_latest.domain.model.PhotosItems
import com.vangelnum.unsplash.feature_latest.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api: ApiInterfaceMain
) : MainRepository {
    override suspend fun getAllMainPhotos(
        page: Int,
        per_page: Int,
        order: String
    ): List<PhotosItems> {
        return api.getPhotos(page, per_page, order).map {
            it.toNormalPhotos()
        }
    }

}