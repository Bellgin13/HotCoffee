package com.example.hotcoffee.ui.coffee

import androidx.lifecycle.*
import com.example.hotcoffee.model.Coffee
import com.example.hotcoffee.repository.CoffeeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoffeeViewModel : ViewModel() {

    private val repository = CoffeeRepository()

    private val _coffees = MutableLiveData<List<Coffee>>()
    val coffees: LiveData<List<Coffee>> get() = _coffees

    init {
        fetchCoffees()
    }

    private fun fetchCoffees() {
        viewModelScope.launch {
            try {
                delay(1000)
                val coffeeList = repository.getHotCoffees()
                _coffees.value = coffeeList
            } catch (e: Exception) {
                // Hata loglama yapılabilir
            }
        }
    }
    // Test functions.
    fun setCoffees(coffeeList: List<Coffee>) {
        _coffees.value = coffeeList
    }
    private val _filteredCoffees = MutableLiveData<List<Coffee>>()
    val filteredCoffees: LiveData<List<Coffee>> get() = _filteredCoffees

    private var selectedFilters: Set<String> = emptySet()

    fun updateFilters(filters: Set<String>) {
        selectedFilters = filters
        applyFilters()
    }

    private fun applyFilters() {
        _filteredCoffees.value = _coffees.value?.filter { coffee ->
            selectedFilters.isEmpty() || selectedFilters.any { filter ->
                coffee.ingredients.any { it.equals(filter, ignoreCase = true) }
            }
        }
    }


}
