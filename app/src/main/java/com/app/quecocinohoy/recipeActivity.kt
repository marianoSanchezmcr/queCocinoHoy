package com.app.quecocinohoy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.quecocinohoy.databinding.ActivityRecipeBinding
import com.google.android.gms.ads.AdRequest

class recipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initUi()

    }

    private fun initUi() {
        initAds()
        initIngredients()
        initInstructions()
    }

    private fun initAds() {
            val adRequest = AdRequest.Builder().build()
            binding.banner.loadAd(adRequest)
    }

    private fun initInstructions() {
        binding.tvInstructions.text = intent.getStringExtra("instructions")
    }

    private fun initIngredients() {
        binding.tvIngredients.text = intent.getStringExtra("ingredients")
    }
}