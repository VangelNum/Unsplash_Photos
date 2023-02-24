package com.vangelnum.unsplash.feature_popular.domain.model

import com.vangelnum.unsplash.core.data.dtoMainAndRandom.Urls

data class PopularItems(
    val color: String,
    val height: Int,
    val width: Int,
    val id: String,
    val urls: Urls
)
