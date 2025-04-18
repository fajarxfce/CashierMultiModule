package com.fajarxfce.shopping.ui


sealed class ShoppingUiState<out T> {
    object Loading : ShoppingUiState<Nothing>()
    data class Success<T>(val data: T) : ShoppingUiState<T>()
    data class Error(val exception: Throwable) : ShoppingUiState<Nothing>()
}