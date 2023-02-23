package com.vangelnum.unsplash.feature_main.data.repository

import com.vangelnum.unsplash.feature_main.data.api.ApiInterfaceMain
import com.vangelnum.unsplash.feature_main.data.mapper.toNormalPhotos
import com.vangelnum.unsplash.feature_main.domain.model.PhotosItems
import com.vangelnum.unsplash.feature_main.domain.repository.MainRepository
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