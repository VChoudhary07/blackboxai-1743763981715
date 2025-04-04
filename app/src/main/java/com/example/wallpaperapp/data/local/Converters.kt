package com.example.wallpaperapp.data.local

import androidx.room.TypeConverter
import com.example.wallpaperapp.data.model.ImageSource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromImageSource(imageSource: ImageSource): String {
        return gson.toJson(imageSource)
    }

    @TypeConverter
    fun toImageSource(json: String): ImageSource {
        return gson.fromJson(json, ImageSource::class.java)
    }
}