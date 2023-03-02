package com.vangelnum.unsplash.feature_random.domain.model


data class PhotosItemsRandom(
    val color: String,
    val height: Int,
    val width: Int,
    val id: String,
    val urls: UrlsRandom,
    val likes: Int,
    val created_at: String,
    val user: UserRandom
)

data class UserRandom(
    val profile_image: ProfileImageRandom,
    val username: String,
    val location: String
)

data class ProfileImageRandom(
    val large: String,
    val medium: String,
    val small: String
)

data class UrlsRandom(
    val full: String,
    val raw: String,
    val regular: String,
)