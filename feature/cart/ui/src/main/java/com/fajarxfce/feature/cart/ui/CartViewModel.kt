package com.fajarxfce.feature.cart.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajarxfce.core.result.onFailure
import com.fajarxfce.core.result.onSuccess
import com.fajarxfce.core.ui.delegate.mvi.MVI
import com.fajarxfce.core.ui.delegate.mvi.mvi
import com.fajarxfce.feature.cart.domain.model.CartItem
import com.fajarxfce.feature.cart.domain.usecase.CreateTransactionUseCase
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
    private val createTransactionUseCase: CreateTransactionUseCase,
) : ViewModel(),
    MVI<CartContract.UiState, CartContract.UiAction, CartContract.UiEffect> by mvi(initialState = CartContract.UiState()) {

    override fun onAction(action: CartContract.UiAction) {
        when (action) {
            is CartContract.UiAction.OnCheckout -> {
                createTransaction(action.cartItems)
            }
            CartContract.UiAction.OnLoad -> {
                getCartItems()
            }

            is CartContract.UiAction.OnDecreaseQuantity -> {
                decreaseProductQuantity(action.productId)
            }

            is CartContract.UiAction.OnIncreaseQuantity -> {
                increaseProductQuantity(action.productId)
            }

            CartContract.UiAction.OnCreateTransaction -> {

            }
        }
    }

    private fun createTransaction(cartItems: List<CartItem>) {
        viewModelScope.launch {
            createTransactionUseCase(cartItems)
                .onSuccess {
                    emitUiEffect(CartContract.UiEffect.ShowSnackbar("Transaction created"))
                }
                .onFailure {
                    emitUiEffect(CartContract.UiEffect.ShowSnackbar(it.message.toString()))
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