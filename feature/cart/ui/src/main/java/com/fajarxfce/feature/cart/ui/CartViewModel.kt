package com.fajarxfce.feature.cart.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajarxfce.core.domain.usecase.DecreaseProductQuantityUseCase
import com.fajarxfce.core.domain.usecase.DeleteProductByIdUseCase
import com.fajarxfce.core.domain.usecase.GetCartItemsUseCase
import com.fajarxfce.core.domain.usecase.IncreaseProductQuantityUseCase
import com.fajarxfce.core.result.onFailure
import com.fajarxfce.core.result.onSuccess
import com.fajarxfce.core.ui.delegate.mvi.MVI
import com.fajarxfce.core.ui.delegate.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val increaseProductQuantityUseCase: IncreaseProductQuantityUseCase,
    private val decreaseProductQuantityUseCase: DecreaseProductQuantityUseCase,
    private val deleteProductByIdUseCase: DeleteProductByIdUseCase,
) : ViewModel(),
    MVI<CartContract.UiState, CartContract.UiAction, CartContract.UiEffect> by mvi(initialState = CartContract.UiState()) {

    init {
        onAction(CartContract.UiAction.OnLoad)
    }

    override fun onAction(action: CartContract.UiAction) {
        when (action) {
            is CartContract.UiAction.OnCheckout -> {}
            is CartContract.UiAction.OnLoad -> loadCarts()
            is CartContract.UiAction.OnDecreaseQuantity -> decreaseProductQuantity(action.productId)
            is CartContract.UiAction.OnIncreaseQuantity -> increaseProductQuantity(action.productId)
            is CartContract.UiAction.OnCreateTransaction -> {}
            is CartContract.UiAction.DeleteCartItem -> deleteCartItemByProductId(action.productId)
        }
    }

    private fun loadCarts() = viewModelScope.launch {
        getCartItemsUseCase().collect { carts ->
            updateUiState { copy(cartItems = carts) }
        }
    }

    private fun increaseProductQuantity(productId: Int) = viewModelScope.launch {
        increaseProductQuantityUseCase(productId)
            .onSuccess {  }
            .onFailure {  }
    }

    private fun decreaseProductQuantity(productId: Int) = viewModelScope.launch {
        decreaseProductQuantityUseCase(productId)
            .onSuccess {  }
            .onFailure {  }
    }

    private fun deleteCartItemByProductId(productId: Int) = viewModelScope.launch {
        deleteProductByIdUseCase(productId)
            .onSuccess {  }
            .onFailure {  }
    }
}