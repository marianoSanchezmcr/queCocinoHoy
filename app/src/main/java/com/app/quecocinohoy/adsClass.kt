package com.app.quecocinohoy

import android.app.Application
import com.google.android.gms.ads.MobileAds

class adsClass:Application() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
    }
}