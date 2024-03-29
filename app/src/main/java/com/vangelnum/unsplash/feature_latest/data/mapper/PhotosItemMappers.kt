package com.vangelnum.unsplash.feature_latest.data.mapper

import com.vangelnum.unsplash.core.data.dtoMainRandomPopular.PhotosItemDto
import com.vangelnum.unsplash.feature_latest.domain.model.PhotosItems


fun PhotosItemDto.toNormalPhotos(): PhotosItems {
    return PhotosItems(
        color = color,
        height = height,
        width = width,
        id = id,
        urls = urls
    )
}

