package com.fajarxfce.shopping.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fajarxfce.core.domain.usecase.product.GetPagingProductUseCase
import com.fajarxfce.core.model.data.product.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ShoppingScreenState(
    val productsState: ShoppingUiState<Flow<PagingData<Product>>> = ShoppingUiState.Loading,
    val cartItems: Map<Product, Int> = emptyMap(),
    val orderBy: String = "products.id",
    val ascending: Boolean = true
)

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val getPagingProductUseCase: GetPagingProductUseCase
) : ViewModel() {

    // Private mutable state
    private val _screenState = MutableStateFlow(ShoppingScreenState())
    private val _cartItems = MutableStateFlow<MutableMap<Product, Int>>(mutableMapOf())

    // Public immutable state
    val uiState = _screenState
        .onStart {
            loadProducts(_screenState.value.orderBy, _screenState.value.ascending)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = _screenState.value
        )

    val cartItems: StateFlow<Map<Product, Int>> = _cartItems
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = emptyMap()
        )

    private fun loadProducts(orderBy: String = "products.id", ascending: Boolean = true) {
        viewModelScope.launch {
            _screenState.update { it.copy(productsState = ShoppingUiState.Loading) }

            try {
                val pagingFlow = getPagingProductUseCase(orderBy, ascending)
                    .cachedIn(viewModelScope)

                _screenState.update {
                    it.copy(
                        productsState = ShoppingUiState.Success(pagingFlow),
                        orderBy = orderBy,
                        ascending = ascending
                    )
                }
            } catch (e: Exception) {
                _screenState.update { it.copy(productsState = ShoppingUiState.Error(e)) }
            }
        }
    }

    fun refreshProducts() {
        loadProducts(_screenState.value.orderBy, _screenState.value.ascending)
    }

    fun sortProducts(orderBy: String, ascending: Boolean) {
        val currentState = _screenState.value
        if (orderBy != currentState.orderBy || ascending != currentState.ascending) {
            loadProducts(orderBy, ascending)
        }
    }

    fun addToCart(product: Product, quantity: Int) {
        _cartItems.update { currentCart ->
            val newCart = currentCart.toMutableMap()
            newCart[product] = (newCart[product] ?: 0) + quantity
            newCart
        }
    }

    fun removeFromCart(product: Product, quantity: Int) {
        _cartItems.update { currentCart ->
            val newCart = currentCart.toMutableMap()
            val currentQuantity = newCart[product] ?: 0
            if (currentQuantity <= quantity) {
                newCart.remove(product)
            } else {
                newCart[product] = currentQuantity - quantity
            }
            newCart
        }
    }

    fun clearCart() {
        _cartItems.value = mutableMapOf()
    }
}