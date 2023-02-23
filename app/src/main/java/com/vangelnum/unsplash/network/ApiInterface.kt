package com.vangelnum.unsplash.network

import com.vangelnum.unsplash.core.data.dtoMainAndRandom.PhotosItemDto
import com.vangelnum.unsplash.dataclass.forsearchphotos.SearchItems
import com.vangelnum.unsplash.core.utils.Constants.API_KEY
import com.vangelnum.unsplash.core.utils.Constants.BASE_URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query




interface ApiInterface {
    @GET("photos/?client_id=$API_KEY")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("order_by") order_by: String
    ): Response<List<PhotosItemDto>>

    @GET("photos/random/?client_id=$API_KEY")
    suspend fun getRandomPhotos(
        @Query("count") count: Int,
    ): Response<List<PhotosItemDto>>

    @GET("/search/photos/?client_id=$API_KEY")
    suspend fun getSearchPhotos(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("per_page") per_page: Int = 30
    ): SearchItems

    companion object {
        var retrofit: ApiInterface? = null
        fun create(): ApiInterface {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiInterface::class.java)

            }
            return retrofit!!
        }
    }

}