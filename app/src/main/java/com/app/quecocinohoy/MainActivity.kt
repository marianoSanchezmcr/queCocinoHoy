package com.app.quecocinohoy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.quecocinohoy.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class MainActivity : AppCompatActivity() {

    private lateinit var biniding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        biniding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(biniding.root)
        initUi()
    }

    private fun initUi() {
        biniding.btnMain.setOnClickListener {
            startActivity(Intent(this, foodActivity::class.java))
        }


    }
}