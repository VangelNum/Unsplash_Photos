package com.vangelnum.unsplash.feature_random.data.mappers

import com.vangelnum.unsplash.core.data.dtoMainAndRandom.PhotosItemDto
import com.vangelnum.unsplash.feature_random.domain.model.PhotosItemsRandom

fun PhotosItemDto.toNormalRandomPhotos() : PhotosItemsRandom {
    return PhotosItemsRandom(
        color = color,
        height = height,
        width = width,
        id = id,
        urls = urls
    )
}