package com.example.jetpackcompose.network

import com.example.jetpackcompose.model.FoodResponse
import retrofit2.Response
import retrofit2.http.GET

interface FoodService {
    @GET("categories.php")
    suspend fun getFoods(): Response<FoodResponse>
}