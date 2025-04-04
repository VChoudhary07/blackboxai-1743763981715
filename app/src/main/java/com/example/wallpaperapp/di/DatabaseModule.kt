package com.example.wallpaperapp.di

import android.content.Context
import androidx.room.Room
import com.example.wallpaperapp.data.local.WallpaperDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WallpaperDatabase {
        return Room.databaseBuilder(
            context,
            WallpaperDatabase::class.java,
            WallpaperDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
         .build()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(database: WallpaperDatabase) = database.favoriteDao()
}