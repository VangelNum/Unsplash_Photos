package com.vangelnum.unsplash.feature_latest.domain.model

import com.vangelnum.unsplash.core.data.dtoMainRandomPopular.Urls


data class PhotosItems(
    val color: String,
    val height: Int,
    val width: Int,
    val id: String,
    val urls: Urls
)