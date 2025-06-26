package com.example.hotcoffee.network

import com.example.hotcoffee.model.Coffee
import retrofit2.http.GET

interface CoffeeApi {
    @GET("coffee/hot")
    suspend fun getHotCoffees(): List<Coffee>
}
