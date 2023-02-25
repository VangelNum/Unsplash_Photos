package com.vangelnum.unsplash.feature_random.data.api

import com.vangelnum.unsplash.core.data.dtoMainRandomPopular.PhotosItemDto
import com.vangelnum.unsplash.core.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterfaceRandom {
    @GET("photos/random/?client_id=${Constants.API_KEY}")
    suspend fun getRandomPhotos(
        @Query("count") count: Int,
    ): List<PhotosItemDto>
}