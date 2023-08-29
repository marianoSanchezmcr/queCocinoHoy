package com.app.quecocinohoy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.app.quecocinohoy.databinding.ActivityFoodBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class foodActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var ingredients: String
    private lateinit var instructions: String
    private var interstitial: InterstitialAd? = null
    private var counter: Int = 0

    private lateinit var binding: ActivityFoodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFoodBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initUi()


    }

    private fun initUi() {

        initAds()
        setFood()
        binding.btnNewFood.isEnabled = false
        initNewFoodButton()
        initRecipeButton()
        initSearchButton()

    }

    private fun initAds() {
        initBanner()
        initAdsInterstitial()

    }

    private fun initBanner() {
        val adRequest = AdRequest.Builder().build()
        binding.banner.loadAd(adRequest)
    }

    private fun initSearchButton() {
        binding.llSearchButton.setOnClickListener {
            startActivity(Intent(this, searchActivity::class.java))
        }
    }

    private fun initRecipeButton() {
        binding.btnRecipe.setOnClickListener {
            val recipeIntent = Intent(this, recipeActivity::class.java)
            recipeIntent.putExtra("ingredients", ingredients)
            recipeIntent.putExtra("instructions", instructions)
            startActivity(recipeIntent)
        }
    }

    private fun setFood() {
        getRandomFood()
    }

    private fun initNewFoodButton() {

        binding.btnNewFood.setOnClickListener {
            it.isEnabled = false
            counter += 1
            setFood()
            checkCounter()
        }
    }

    private fun getRandomFood() {
        firestore  = FirebaseFirestore.getInstance()


        firestore.collection("comidas").get().addOnSuccessListener { documents ->
                binding.btnNewFood.isEnabled = true
                val foodList = documents.documents.toList()
                val position = (foodList.indices).random()
                val food = foodList[position].getString("name")
                ingredients = foodList[position].getString("ingredientes")!!
                instructions = foodList[position].getString("instrucciones")!!
                food?.let {
                    binding.tvFood.text = it
                }
            }.addOnFailureListener {
                Toast.makeText(this, getString(R.string.connect_to_internet), Toast.LENGTH_SHORT).show()
            }


    }
    private fun initAdsInterstitial() {
        var adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, "ca-app-pub-38400000-8cf0-11bd-b23e-10b96e40000d", adRequest, object : InterstitialAdLoadCallback(){
            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                interstitial = interstitialAd
            }
            override fun onAdFailedToLoad(p0: LoadAdError) {
                interstitial = null
            }
        })


    }
    private fun showAds(){
        interstitial?.show(this)
    }
    private fun checkCounter() {
        if (counter >= 4){
            showAds()
            counter = 0
            initAdsInterstitial()
        }
    }

}