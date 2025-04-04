package com.example.wallpaperapp.data.model

import com.google.gson.annotations.SerializedName

data class WallpaperResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("per_page") val perPage: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("next_page") val nextPage: String?,
    @SerializedName("photos") val wallpapers: List<Wallpaper>
)

data class Wallpaper(
    @SerializedName("id") val id: Int,
    @SerializedName("photographer") val photographer: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("src") val src: ImageSource
)

data class ImageSource(
    @SerializedName("original") val original: String,
    @SerializedName("large2x") val large2x: String,
    @SerializedName("large") val large: String,
    @SerializedName("medium") val medium: String,
    @SerializedName("small") val small: String,
    @SerializedName("portrait") val portrait: String,
    @SerializedName("landscape") val landscape: String,
    @SerializedName("tiny") val tiny: String
)