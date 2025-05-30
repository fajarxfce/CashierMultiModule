package com.fajarxfce.feature.cart.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajarxfce.core.ui.delegate.mvi.MVI
import com.fajarxfce.core.ui.delegate.mvi.mvi
import com.fajarxfce.feature.cart.domain.usecase.GetCartItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
) : ViewModel(),
    MVI<CartContract.UiState, CartContract.UiAction, CartContract.UiEffect> by mvi(initialState = CartContract.UiState()) {

    override fun onAction(action: CartContract.UiAction) {
        when (action) {
            CartContract.UiAction.OnCheckout -> {}
            CartContract.UiAction.OnLoad -> {
                getCartItems()
            }
        }
    }

    private fun getCartItems() {
        viewModelScope.launch {
            getCartItemsUseCase().collect {
                Timber.tag("CartViewModel").d("getCartItems: $it")
            }
        }
    }
}