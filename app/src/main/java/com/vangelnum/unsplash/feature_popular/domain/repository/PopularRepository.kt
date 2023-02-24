package com.vangelnum.unsplash.feature_popular.domain.repository

import com.vangelnum.unsplash.feature_popular.domain.model.PopularItems

interface PopularRepository {
    suspend fun getAllPopularPhotos(page: Int, per_page: Int, order: String): List<PopularItems>
}