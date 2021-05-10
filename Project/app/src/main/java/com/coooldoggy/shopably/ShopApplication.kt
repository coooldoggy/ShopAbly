package com.coooldoggy.shopably

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShopApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(applicationContext)
    }
}