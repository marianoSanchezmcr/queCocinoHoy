package com.app.quecocinohoy

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class itemAdapter(val itemList: List<recipe>, private val onClickListener:(recipe) -> Unit):RecyclerView.Adapter<itemViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return itemViewHolder(layoutInflater.inflate(R.layout.recipe_item, parent, false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {
        val item = itemList[position]
        holder.render(item, onClickListener)
    }
}