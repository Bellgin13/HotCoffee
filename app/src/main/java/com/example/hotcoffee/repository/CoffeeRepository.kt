package com.example.hotcoffee.repository

import com.example.hotcoffee.model.Coffee
import com.example.hotcoffee.network.RetrofitInstance

class CoffeeRepository {
    suspend fun getHotCoffees(): List<Coffee> {
        return RetrofitInstance.api.getHotCoffees()
    }
}
