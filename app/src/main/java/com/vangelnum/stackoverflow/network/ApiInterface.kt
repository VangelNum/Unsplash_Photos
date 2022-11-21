package com.vangelnum.stackoverflow.network

import com.vangelnum.stackoverflow.dataclass.forlistphotos.PhotosItem
import com.vangelnum.stackoverflow.dataclass.forsearchphotos.Result
import com.vangelnum.stackoverflow.dataclass.forsearchphotos.SearchItems
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL = "https://api.unsplash.com"
const val API_KEY = "eaNFANoAIUun1E3nnRkuMemTeRy57UmCvHIymIf7B28"

interface ApiInterface {
    @GET("photos/?client_id=$API_KEY")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("order_by") order_by: String
    ): Response<List<PhotosItem>>

    @GET("photos/random/?client_id=$API_KEY")
    suspend fun getRandomPhotos(
        @Query("count") count: Int,
    ): Response<List<PhotosItem>>

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