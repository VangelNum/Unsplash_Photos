package com.vangelnum.unsplash.feature_random.domain.model

import com.vangelnum.unsplash.core.data.dtoMainAndRandom.Urls


data class PhotosItemsRandom(
    val color: String,
    val height: Int,
    val width: Int,
    val id: String,
    val urls: Urls
)