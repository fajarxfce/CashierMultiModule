package com.fajarxfce.feature.cart.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajarxfce.core.ui.delegate.mvi.MVI
import com.fajarxfce.core.ui.delegate.mvi.mvi
import com.fajarxfce.feature.cart.domain.usecase.DecreaseProductQuantityUseCase
import com.fajarxfce.feature.cart.domain.usecase.GetCartItemsUseCase
import com.fajarxfce.feature.cart.domain.usecase.IncreaseProductQuantityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val increaseProductQuantityUseCase: IncreaseProductQuantityUseCase,
    private val decreaseProductQuantityUseCase: DecreaseProductQuantityUseCase,
) : ViewModel(),
    MVI<CartContract.UiState, CartContract.UiAction, CartContract.UiEffect> by mvi(initialState = CartContract.UiState()) {

    override fun onAction(action: CartContract.UiAction) {
        when (action) {
            CartContract.UiAction.OnCheckout -> {}
            CartContract.UiAction.OnLoad -> {
                getCartItems()
            }

            is CartContract.UiAction.OnDecreaseQuantity -> {
                decreaseProductQuantity(action.productId)
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

    private fun decreaseProductQuantity(productId: Int) {
        viewModelScope.launch {
            decreaseProductQuantityUseCase(productId)
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