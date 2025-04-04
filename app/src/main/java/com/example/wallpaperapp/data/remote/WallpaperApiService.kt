package com.example.wallpaperapp.data.remote

import com.example.wallpaperapp.data.model.WallpaperResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WallpaperApiService {
    @GET("v1/curated")
    suspend fun getWallpapers(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30,
        @Header("Authorization") apiKey: String = "YOUR_PEXELS_API_KEY"
    ): Response<WallpaperResponse>

    companion object {
        const val BASE_URL = "https://api.pexels.com/"
    }
}