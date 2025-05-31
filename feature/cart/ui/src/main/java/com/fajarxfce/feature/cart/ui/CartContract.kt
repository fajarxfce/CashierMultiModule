package com.fajarxfce.feature.cart.ui

import com.fajarxfce.feature.cart.domain.model.CartItem

object CartContract {
    data class UiState(
        val isLoading: Boolean = false,
        val cartItems: List<CartItem> = emptyList(),
    )

    sealed interface UiAction {
        data object OnLoad : UiAction
        data object OnCheckout : UiAction
        data class OnIncreaseQuantity(val productId: Int) : UiAction
        data class OnDecreaseQuantity(val productId: Int) : UiAction
    }
    sealed interface UiEffect {
        data class ShowSnackbar(val message: String, val isError: Boolean = false) : UiEffect
    }
}