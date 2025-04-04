package com.example.wallpaperapp.data.repository

import com.example.wallpaperapp.data.model.Wallpaper
import com.example.wallpaperapp.data.remote.WallpaperApiService
import com.example.wallpaperapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WallpaperRepositoryImpl(
    private val apiService: WallpaperApiService,
    private val favoriteDao: FavoriteDao
) : WallpaperRepository {

    override suspend fun getWallpaper(wallpaperId: String): Resource<Wallpaper> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getWallpaper(wallpaperId)
                if (response.isSuccessful) {
                    Resource.Success(response.body())
                } else {
                    Resource.Error("Error fetching wallpaper: ${response.message()}")
                }
            } catch (e: Exception) {
                Resource.Error("Network error: ${e.message}")
            }
        }
    }

    override suspend fun getWallpapers(): Resource<List<Wallpaper>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getWallpapers()
                if (response.isSuccessful) {
                    Resource.Success(response.body()?.wallpapers ?: emptyList())
                } else {
                    Resource.Error("Error fetching wallpapers: ${response.message()}")
                }
            } catch (e: Exception) {
                Resource.Error("Network error: ${e.message}")
            }
        }
    }

    override suspend fun refreshWallpapers(): Resource<List<Wallpaper>> {
        return getWallpapers() // Re-fetch wallpapers
    }

    override suspend fun loadMoreWallpapers(): Resource<List<Wallpaper>> {
        // Implement pagination logic here
        return Resource.Success(emptyList()) // Placeholder for pagination
    }

    // Favorites implementation
    override suspend fun getFavorites(): Resource<List<Wallpaper>> {
        return try {
            val favorites = favoriteDao.getAllFavorites()
                .first()
                .map { it.toWallpaper() }
            Resource.Success(favorites)
        } catch (e: Exception) {
            Resource.Error("Error loading favorites: ${e.message}")
        }
    }

    override suspend fun addFavorite(wallpaper: Wallpaper) {
        favoriteDao.insertFavorite(wallpaper.toFavoriteEntity())
    }

    override suspend fun removeFavorite(wallpaperId: Int) {
        favoriteDao.deleteFavoriteById(wallpaperId)
    }

    override suspend fun isFavorite(wallpaperId: Int): Boolean {
        return favoriteDao.isFavorite(wallpaperId)
    }
}