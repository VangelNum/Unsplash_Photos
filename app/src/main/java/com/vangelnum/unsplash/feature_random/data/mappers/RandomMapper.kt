package com.vangelnum.unsplash.feature_random.data.mappers

import com.vangelnum.unsplash.core.data.dtoMainRandomPopular.PhotosItemDto
import com.vangelnum.unsplash.core.data.dtoMainRandomPopular.ProfileImage
import com.vangelnum.unsplash.core.data.dtoMainRandomPopular.Urls
import com.vangelnum.unsplash.core.data.dtoMainRandomPopular.User
import com.vangelnum.unsplash.feature_random.domain.model.PhotosItemsRandom
import com.vangelnum.unsplash.feature_random.domain.model.ProfileImageRandom
import com.vangelnum.unsplash.feature_random.domain.model.UrlsRandom
import com.vangelnum.unsplash.feature_random.domain.model.UserRandom

fun PhotosItemDto.toNormalRandomPhotos(): PhotosItemsRandom {
    return PhotosItemsRandom(
        color = color,
        height = height,
        width = width,
        id = id,
        urls = urls.toDataModel(),
        likes = likes,
        updated_at = updated_at,
        user = user.toDataModel()
    )
}

fun Urls.toDataModel(): UrlsRandom {
    return UrlsRandom(
        raw = raw,
        full = full,
        regular = regular,
    )
}

fun User.toDataModel(): UserRandom {
    return UserRandom(
        username = username,
        profile_image = profile_image.toDataModel()
    )
}

fun ProfileImage.toDataModel(): ProfileImageRandom {
    return ProfileImageRandom(
        small = small,
        medium = medium,
        large = large
    )
}
