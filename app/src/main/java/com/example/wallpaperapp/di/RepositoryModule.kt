package com.example.wallpaperapp.di

import com.example.wallpaperapp.data.remote.WallpaperApiService
import com.example.wallpaperapp.data.repository.WallpaperRepository
import com.example.wallpaperapp.data.repository.WallpaperRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWallpaperRepository(
        apiService: WallpaperApiService,
        favoriteDao: FavoriteDao
    ): WallpaperRepository {
        return WallpaperRepositoryImpl(apiService, favoriteDao)
    }
}