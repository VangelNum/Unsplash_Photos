package com.vangelnum.stackoverflow.network

import com.vangelnum.stackoverflow.dataclass.PhotosItem
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
    ): Response<List<PhotosItem>>

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