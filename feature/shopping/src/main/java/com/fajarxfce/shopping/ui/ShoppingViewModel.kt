package com.fajarxfce.shopping.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val imageUrl: String,
    var quantity: Int = 0
)

@HiltViewModel
class ShoppingViewModel @Inject constructor() : ViewModel() {

    private val _shoppingUiState = MutableStateFlow<ShoppingUiState<List<Product>>>(ShoppingUiState.Loading)
    val shoppingUiState: StateFlow<ShoppingUiState<List<Product>>>
        get() = _shoppingUiState

    fun generateDummyProducts() {
        val dummyProducts = List(10) { index ->
            Product(
                id = "product_$index",
                name = "Product $index",
                price = (index + 1) * 10.0,
                imageUrl = "https://picsum.photos/id/1/300/300"
            )
        }
        _shoppingUiState.value = ShoppingUiState.Success(dummyProducts)
    }
}