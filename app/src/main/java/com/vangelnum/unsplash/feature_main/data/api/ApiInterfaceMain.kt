package com.vangelnum.unsplash.feature_main.data.api

import com.vangelnum.unsplash.core.data.dtoMainRandomPopular.PhotosItemDto
import com.vangelnum.unsplash.core.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterfaceMain {
    @GET("photos/?client_id=$API_KEY")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
        @Query("order_by") order_by: String
    ): List<PhotosItemDto>
}