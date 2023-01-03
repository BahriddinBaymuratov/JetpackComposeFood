package com.example.jetpackcompose.ui.theme.food_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.network.RetroInstance
import com.example.jetpackcompose.repository.FoodRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _state: MutableStateFlow<MainState> = MutableStateFlow(MainState())
    val state: StateFlow<MainState> get() = _state
    private val repository = FoodRepository(RetroInstance.provideFoodService())

    init {
        getFoods()
    }

    private fun getFoods() {
        viewModelScope.launch {
            _state.update {
                MainState(loading = true)
            }
            delay(700L)
            try {
                val response = repository.getFoods()
                if (response.isSuccessful) {
                    _state.update {
                        MainState(loading = false, success = response.body()?.categories!!)
                    }
                }
            } catch (e: Exception) {
                println("@@@ ${e.message}")
                _state.update {
                    MainState(loading = false, error = e.stackTraceToString())
                }
            }
        }
    }
}