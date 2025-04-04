package com.example.wallpaperapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class WallpaperApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Initialize any app-wide components here
        initializeApp()
    }

    private fun initializeApp() {
        // App-wide initialization logic
        // Example: Crash reporting, analytics, etc.
    }
}