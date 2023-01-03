package com.example.jetpackcompose.repository

import com.example.jetpackcompose.network.FoodService

class FoodRepository(
    private val foodService: FoodService
) {
    suspend fun getFoods() = foodService.getFoods()
}