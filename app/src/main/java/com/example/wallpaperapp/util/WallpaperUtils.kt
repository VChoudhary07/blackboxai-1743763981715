package com.example.wallpaperapp.util

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.annotation.DrawableRes
import com.example.wallpaperapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL

object WallpaperUtils {

    suspend fun setAsWallpaper(
        context: Context,
        imageUrl: String,
        @DrawableRes placeholderRes: Int = R.drawable.placeholder
    ) {
        try {
            val bitmap = withContext(Dispatchers.IO) {
                try {
                    URL(imageUrl).openStream().use {
                        BitmapFactory.decodeStream(it)
                    }
                } catch (e: IOException) {
                    BitmapFactory.decodeResource(context.resources, placeholderRes)
                }
            }

            val wallpaperManager = WallpaperManager.getInstance(context)
            wallpaperManager.setBitmap(bitmap)

            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    context.getString(R.string.wallpaper_set_success),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    context.getString(R.string.wallpaper_set_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun canSetWallpaper(context: Context): Boolean {
        return try {
            WallpaperManager.getInstance(context).isSetWallpaperAllowed
        } catch (e: Exception) {
            false
        }
    }
}