package com.vangelnum.unsplash.feature_popular.data.repository

import com.vangelnum.unsplash.feature_popular.data.api.ApiInterfacePopular
import com.vangelnum.unsplash.feature_popular.data.mapper.toNormalPopular
import com.vangelnum.unsplash.feature_popular.domain.model.PopularItems
import com.vangelnum.unsplash.feature_popular.domain.repository.PopularRepository
import javax.inject.Inject

class PopularRepositoryImpl @Inject constructor(
    private val api: ApiInterfacePopular
) : PopularRepository {
    override suspend fun getAllPopularPhotos(
        page: Int,
        per_page: Int,
        order: String
    ): List<PopularItems> {
        return api.getPopularPhotos(page, per_page, order).map {
            it.toNormalPopular()
        }
    }
}