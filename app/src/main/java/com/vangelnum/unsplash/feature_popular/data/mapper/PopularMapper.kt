package com.vangelnum.unsplash.feature_popular.data.mapper

import com.vangelnum.unsplash.core.data.dtoMainRandomPopular.PhotosItemDto
import com.vangelnum.unsplash.feature_popular.domain.model.PopularItems

fun PhotosItemDto.toNormalPopular(): PopularItems {
    return PopularItems(
        color = color,
        height = height,
        width = width,
        id = id,
        urls = urls
    )
}