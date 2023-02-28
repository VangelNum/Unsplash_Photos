package com.vangelnum.unsplash.feature_latest.domain.repository

import com.vangelnum.unsplash.feature_latest.domain.model.PhotosItems

interface MainRepository {
    suspend fun getAllMainPhotos(page: Int, per_page: Int, order: String): List<PhotosItems>
}