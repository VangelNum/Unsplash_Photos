package com.vangelnum.unsplash.feature_popular.data.api

import com.vangelnum.unsplash.core.data.dtoMainAndRandom.PhotosItemDto
import com.vangelnum.unsplash.core.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterfacePopular {
    @GET("photos/?client_id=${Constants.API_KEY}")
    suspend fun getPopularPhotos(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
        @Query("order_by") order_by: String
    ): List<PhotosItemDto>
}