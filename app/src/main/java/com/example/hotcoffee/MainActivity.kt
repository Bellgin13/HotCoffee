package com.example.hotcoffee

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.hotcoffee.databinding.ActivityMainBinding
import com.example.hotcoffee.model.Coffee
import com.example.hotcoffee.ui.coffee.CoffeeAdapter
import com.example.hotcoffee.ui.coffee.CoffeeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CoffeeAdapter
    private val viewModel: CoffeeViewModel by viewModels()
    private var selectedIngredients = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CoffeeAdapter(emptyList()) { coffee ->
            binding.coffeeOverlay.visibility = View.VISIBLE
            binding.detailName.text = coffee.title
            binding.detailDescription.text = coffee.description
            binding.detailIngredients.text = "Ingredients: ${coffee.ingredients.joinToString(", ")}"

            Glide.with(this)
                .load(coffee.image)
                .into(binding.detailImage)

            binding.coffeeOverlay.setOnClickListener {
                binding.coffeeOverlay.visibility = View.GONE
            }
        }


        binding.coffeeRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.coffeeRecyclerView.adapter = adapter

        binding.loadingText.visibility = View.VISIBLE
        binding.loadingProgressBar.visibility = View.VISIBLE

        // Updating the list after filtering
        viewModel.coffees.observe(this) { coffeeList ->
            binding.loadingText.visibility = View.GONE
            binding.loadingProgressBar.visibility = View.GONE

            adapter.updateList(applyFilter(coffeeList))
            setupFilterWidget(coffeeList)
        }


        binding.filterIcon.setOnClickListener {
            val visibility = binding.filterOverlay.visibility
            if (visibility == View.VISIBLE) {
                binding.filterOverlay.visibility = View.GONE
                binding.showResultsButton.visibility = View.GONE
            } else {
                binding.filterOverlay.visibility = View.VISIBLE
                binding.showResultsButton.visibility = View.VISIBLE
            }
        }


        // Show Results button
        binding.showResultsButton.setOnClickListener {
            binding.filterOverlay.visibility = View.GONE
        }

        // Clear Filter button
        binding.clearFilterText.setOnClickListener {
            selectedIngredients.clear()
            updateCheckboxes()
            viewModel.coffees.value?.let {
                adapter.updateList(applyFilter(it))
            }
        }
    }

    private fun setupFilterWidget(coffeeList: List<Coffee>) {
        val allIngredients = coffeeList.flatMap { it.ingredients }.toSet().distinct().sortedBy { it.lowercase() }
        //ingredient list gives duplicate results but they output different results.
        val filterContent = binding.filterContainer

        filterContent.removeAllViews()

        allIngredients.forEach { ingredient ->
            val checkBox = CheckBox(this).apply {
                text = ingredient
                isChecked = selectedIngredients.contains(ingredient)
                setTextColor(android.graphics.Color.WHITE)
                buttonTintList = ColorStateList.valueOf(android.graphics.Color.WHITE)
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedIngredients.add(ingredient)
                    } else {
                        selectedIngredients.remove(ingredient)
                    }
                    viewModel.coffees.value?.let {
                        adapter.updateList(applyFilter(it))
                    }
                }
            }
            filterContent.addView(checkBox)
        }
    }

    private fun updateCheckboxes() {
        val filterContent = binding.filterContainer
        for (i in 0 until filterContent.childCount) {
            val view = filterContent.getChildAt(i)
            if (view is CheckBox) {
                view.isChecked = selectedIngredients.contains(view.text.toString())
            }
        }
    }

    private fun applyFilter(coffeeList: List<Coffee>): List<Coffee> {
        if (selectedIngredients.isEmpty()) return coffeeList
        return coffeeList.filter { coffee ->
            selectedIngredients.any { it in coffee.ingredients }
        }
    }
}
