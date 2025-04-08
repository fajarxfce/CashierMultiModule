package com.fajarxfce.shopping.ui

import androidx.lifecycle.ViewModel
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

class ShoppingViewModel : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(generateDummyProducts())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    fun updateQuantity(productId: String, quantity: Int) {
        _products.update { currentList ->
            currentList.map { product ->
                if (product.id == productId) {
                    product.copy(quantity = quantity)
                } else {
                    product
                }
            }
        }
    }

    private fun generateDummyProducts(): List<Product> {
        return listOf(
            Product("1", "Smartphone", 699.99, "https://picsum.photos/id/1/300/300"),
            Product("2", "Laptop", 1299.99, "https://picsum.photos/id/2/300/300"),
            Product("3", "Headphones", 149.99, "https://picsum.photos/id/3/300/300"),
            Product("4", "Smartwatch", 249.99, "https://picsum.photos/id/4/300/300"),
            Product("5", "Tablet", 399.99, "https://picsum.photos/id/5/300/300"),
            Product("6", "Bluetooth Speaker", 89.99, "https://picsum.photos/id/6/300/300"),
            Product("7", "Camera", 499.99, "https://picsum.photos/id/7/300/300"),
            Product("8", "Gaming Console", 499.99, "https://picsum.photos/id/8/300/300")
        )
    }
}