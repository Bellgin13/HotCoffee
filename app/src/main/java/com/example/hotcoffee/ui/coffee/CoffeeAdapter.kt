package com.example.hotcoffee.ui.coffee

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotcoffee.R
import com.example.hotcoffee.model.Coffee

class CoffeeAdapter(private var coffeeList: List<Coffee>,
                    private val onItemClick: (Coffee) -> Unit) :
    RecyclerView.Adapter<CoffeeAdapter.CoffeeViewHolder>() {

    class CoffeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coffee, parent, false)
        return CoffeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoffeeViewHolder, position: Int) {
        val coffee = coffeeList[position]
        holder.titleTextView.text = coffee.title
        holder.descriptionTextView.text = coffee.description

        holder.itemView.setOnClickListener {
            onItemClick(coffee)
        }

        Glide.with(holder.itemView.context)
            .load(coffee.image)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = coffeeList.size

    fun updateList(newList: List<Coffee>) {
        val diffCallback = CoffeeDiffUtil(coffeeList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        coffeeList = newList
        diffResult.dispatchUpdatesTo(this)
    }


}
