package com.fajarxfce.feature.cart.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajarxfce.core.ui.delegate.mvi.MVI
import com.fajarxfce.core.ui.delegate.mvi.mvi
import com.fajarxfce.feature.cart.domain.usecase.GetCartItemsUseCase
import com.fajarxfce.feature.cart.domain.usecase.IncreaseProductQuantityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val increaseProductQuantityUseCase: IncreaseProductQuantityUseCase,
) : ViewModel(),
    MVI<CartContract.UiState, CartContract.UiAction, CartContract.UiEffect> by mvi(initialState = CartContract.UiState()) {

    override fun onAction(action: CartContract.UiAction) {
        when (action) {
            CartContract.UiAction.OnCheckout -> {}
            CartContract.UiAction.OnLoad -> {
                getCartItems()
            }

            is CartContract.UiAction.OnDecreaseQuantity -> {

            }
            is CartContract.UiAction.OnIncreaseQuantity -> {
                increaseProductQuantity(action.productId)
            }
        }
    }

    private fun increaseProductQuantity(productId: Int) {
        viewModelScope.launch {
            increaseProductQuantityUseCase(productId)
        }
    }

    private fun getCartItems() {
        viewModelScope.launch {
            getCartItemsUseCase().collect { items ->
                updateUiState { copy(cartItems = items) }
            }
        }
    }
}