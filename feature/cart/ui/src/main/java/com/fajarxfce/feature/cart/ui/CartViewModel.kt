package com.fajarxfce.feature.cart.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajarxfce.core.domain.usecase.GetCartItemsUseCase
import com.fajarxfce.core.ui.delegate.mvi.MVI
import com.fajarxfce.core.ui.delegate.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
) : ViewModel(),
    MVI<CartContract.UiState, CartContract.UiAction, CartContract.UiEffect> by mvi(initialState = CartContract.UiState()) {

    init {
        onAction(CartContract.UiAction.OnLoad)
    }

    override fun onAction(action: CartContract.UiAction) {
        when (action) {
            is CartContract.UiAction.OnCheckout -> {
            }

            CartContract.UiAction.OnLoad -> {
                loadCarts()
            }

            is CartContract.UiAction.OnDecreaseQuantity -> {
            }

            is CartContract.UiAction.OnIncreaseQuantity -> {

            }

            CartContract.UiAction.OnCreateTransaction -> {

            }
        }
    }

    private fun loadCarts() = viewModelScope.launch {
        getCartItemsUseCase().collect { carts ->
            updateUiState { copy(cartItems = carts) }
        }
    }
}