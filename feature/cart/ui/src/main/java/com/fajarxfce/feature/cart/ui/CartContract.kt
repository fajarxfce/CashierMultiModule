package com.fajarxfce.feature.cart.ui

import com.fajarxfce.core.model.cart.CartItem

object CartContract {
    data class UiState(
        val isLoading: Boolean = false,
        val cartItems: List<CartItem> = emptyList(),
    )

    sealed interface UiAction {
        data object OnLoad : UiAction
        data class OnCheckout(val cartItems: List<CartItem>) : UiAction
        data object OnCreateTransaction : UiAction
        data class OnIncreaseQuantity(val productId: Int) : UiAction
        data class OnDecreaseQuantity(val productId: Int) : UiAction
        data class DeleteCartItem(val productId: Int) : UiAction
    }
    sealed interface UiEffect {
        data class ShowSnackbar(val message: String, val isError: Boolean = false) : UiEffect
    }
}