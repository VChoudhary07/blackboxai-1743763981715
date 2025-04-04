package com.example.wallpaperapp.data.repository

import com.example.wallpaperapp.data.model.Wallpaper
import com.example.wallpaperapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface WallpaperRepository {
    // Network operations
    suspend fun getWallpapers(): Resource<List<Wallpaper>>
    suspend fun getWallpaper(wallpaperId: String): Resource<Wallpaper>
    suspend fun refreshWallpapers(): Resource<List<Wallpaper>>
    suspend fun loadMoreWallpapers(): Resource<List<Wallpaper>>

    // Favorites operations
    suspend fun getFavorites(): Resource<List<Wallpaper>>
    suspend fun addFavorite(wallpaper: Wallpaper)
    suspend fun removeFavorite(wallpaperId: Int)
    suspend fun isFavorite(wallpaperId: Int): Boolean
}
