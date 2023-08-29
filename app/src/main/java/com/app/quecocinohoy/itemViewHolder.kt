package com.app.quecocinohoy

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.app.quecocinohoy.databinding.RecipeItemBinding

class itemViewHolder(view: View): RecyclerView.ViewHolder(view)  {
    var binding = RecipeItemBinding.bind(view)

    fun render(recipe: recipe, onClickListener:(recipe) -> Unit){

        binding.tvName.text = recipe.name
        itemView.setOnClickListener{
            onClickListener(recipe)
        }

    }
}