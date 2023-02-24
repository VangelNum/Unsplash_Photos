package com.vangelnum.unsplash.feature_search.data.api

import com.vangelnum.unsplash.core.utils.Constants
import com.vangelnum.unsplash.feature_search.data.dto.SearchItemsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterfaceSearch {
    @GET("/search/photos/?client_id=${Constants.API_KEY}")
    suspend fun getSearchPhotos(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("per_page") per_page: Int
    ): SearchItemsDto
}