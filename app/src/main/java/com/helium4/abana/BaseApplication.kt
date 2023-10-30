package com.helium4.abana

import android.app.Application
import androidx.startup.AppInitializer
import app.rive.runtime.kotlin.RiveInitializer

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AppInitializer.getInstance(applicationContext)
            .initializeComponent(RiveInitializer::class.java)
    }
}