package com.vangelnum.unsplash.feature_main.domain.repository

import com.vangelnum.unsplash.feature_main.domain.model.PhotosItems

interface MainRepository {
    suspend fun getAllMainPhotos(page: Int, per_page: Int, order: String): List<PhotosItems>
}