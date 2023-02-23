package com.vangelnum.unsplash.feature_main.data.mapper

import com.vangelnum.unsplash.core.data.dtoMainAndRandom.PhotosItemDto
import com.vangelnum.unsplash.feature_main.domain.model.PhotosItems


fun PhotosItemDto.toNormalPhotos(): PhotosItems {
    return PhotosItems(
        color = color,
        height = height,
        width = width,
        id = id,
        urls = urls
    )
}

