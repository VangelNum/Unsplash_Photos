package com.vangelnum.unsplash.feature_main.domain.model

import com.vangelnum.unsplash.core.data.dtoMainAndRandom.Urls


data class PhotosItems(
    val color: String,
    val height: Int,
    val width: Int,
    val id: String,
    val urls: Urls
)