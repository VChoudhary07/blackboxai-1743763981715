package com.example.wallpaperapp.data.model

data class Wallpaper(
    val id: String,
    val photographer: String,
    val src: ImageSource,
    val width: Int,
    val height: Int
)

data class ImageSource(
    val original: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String
)