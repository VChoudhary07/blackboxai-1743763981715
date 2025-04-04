package com.example.wallpaperapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wallpaperapp.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteEntity)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorites WHERE id = :wallpaperId")
    suspend fun getFavoriteById(wallpaperId: Int): FavoriteEntity?

    @Query("DELETE FROM favorites WHERE id = :wallpaperId")
    suspend fun deleteFavoriteById(wallpaperId: Int)

    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE id = :wallpaperId)")
    suspend fun isFavorite(wallpaperId: Int): Boolean
}