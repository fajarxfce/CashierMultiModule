package com.fajarxfce.shopping.ui


sealed class ShoppingUiState<out T: Any?> {

    data object Loading : ShoppingUiState<Nothing>()

    data class Success<out T: Any>(val data: T) : ShoppingUiState<T>()

    data class Error(val exception: Throwable) : ShoppingUiState<Nothing>()
}