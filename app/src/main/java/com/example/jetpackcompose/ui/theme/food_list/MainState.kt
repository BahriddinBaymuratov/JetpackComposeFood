package com.example.jetpackcompose.ui.theme.food_list

import com.example.jetpackcompose.model.Category

data class MainState(
    val loading: Boolean = false,
    val error: String = "",
    val success: List<Category> = emptyList()
)