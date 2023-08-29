package com.app.quecocinohoy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager

import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.quecocinohoy.databinding.ActivitySearchBinding
import com.google.firebase.firestore.FirebaseFirestore

class searchActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    val itemsList = mutableListOf<recipe>()

    private var searchViewHasContent: Boolean = false


    private lateinit var binding:ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySearchBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initUi()

    }

    private fun initUi() {
        setRecyclerView(emptyList<recipe>().toMutableList())
        manageSearchView()

    }
    private fun manageSearchView() {
        binding.svRecipes.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchViewHasContent = true
                getItems(query)

                hideKeyBoard(binding.svRecipes)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null){
                    getItems(newText)
                }
                return true
            }
        })
    }

    private fun getItems(query: String) {
        itemsList.clear()
        firestore = FirebaseFirestore.getInstance()

        if (searchViewHasContent){
            firestore
                .collection("comidas")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents.documents.toList()){
                        val recipeName = document.getString("name")
                        val recipeIngredients = document.getString("ingredientes")
                        val recipeInstructions = document.getString("instrucciones")

                        if (recipeName != null && recipeIngredients != null && recipeInstructions != null){
                            var recipe = recipe(recipeName, recipeIngredients, recipeInstructions)
                            itemsList.add(recipe)
                        }
                    }

                    var filteredList = itemsList.filter {recipe ->
                        (recipe.name.contains(query.capitalize())
                                || recipe.name.contains(query)
                                || recipe.name.contains(query.toLowerCase())
                                || recipe.name.contains(query.toLowerCase().capitalize()))

                    }
                    setRecyclerView(filteredList.toMutableList())
                }.addOnFailureListener {
                    Toast.makeText(this, getString(R.string.connect_to_internet), Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun hideKeyBoard(view: View) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    private fun setRecyclerView(list:  MutableList<recipe>) {
        val manager =  LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        binding.rvRecipes.layoutManager = manager
        binding.rvRecipes.adapter = itemAdapter(list.toList()) { onItemSelected(it) }
        binding.rvRecipes.addItemDecoration(decoration)
    }

    private fun onItemSelected(recipe: recipe) {
            val intent = Intent(this, recipeActivity::class.java)
            intent.putExtra("instructions", recipe.instructions)
            intent.putExtra("ingredients", recipe.ingredients)
            startActivity(intent)
    }
}


