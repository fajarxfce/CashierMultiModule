package com.fajarxfce.feature.cart.ui

import com.fajarxfce.feature.cart.domain.model.CartItem

object CartContract {
    data class UiState(
        val isLoading: Boolean = false,
        val cartItems: List<CartItem> = emptyList(),
    )

    sealed interface UiAction {
        data object OnCheckout : UiAction
    }
    sealed interface UiEffect {
        data class ShowSnackbar(val message: String, val isError: Boolean = false) : UiEffect
    }
}