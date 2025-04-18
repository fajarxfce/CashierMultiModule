package com.fajarxfce.shopping.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.fajarxfce.core.domain.usecase.product.GetAllProductUseCase
import com.fajarxfce.core.domain.usecase.product.GetPagingProductUseCase
import com.fajarxfce.core.model.data.product.Product
import com.fajarxfce.core.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val getPagingProductUseCase: GetPagingProductUseCase
) : ViewModel() {

    private val _shoppingUiState = MutableStateFlow<ShoppingUiState<Flow<PagingData<Product>>>>( // Updated type
        ShoppingUiState.Loading
    )
    val shoppingUiState: StateFlow<ShoppingUiState<Flow<PagingData<Product>>>> = _shoppingUiState.asStateFlow() // Updated type

    private val _cartItems = MutableStateFlow<MutableMap<Product, Int>>(mutableMapOf())
    val cartItems: StateFlow<Map<Product, Int>> = _cartItems.asStateFlow()

    // products in cart with quantityfirefox

    // Keep reference to the current flow for refreshing purposes
    private var currentProductFlow: Flow<PagingData<Product>>? = null
    private var currentOrderBy: String = "products.id"
    private var currentAscending: Boolean = true

    init {
        loadProducts()
    }
    private fun loadProducts(orderBy: String = currentOrderBy, ascending: Boolean = currentAscending) {
        viewModelScope.launch {
            _shoppingUiState.update { ShoppingUiState.Loading }

            try {
                currentOrderBy = orderBy
                currentAscending = ascending

                val pagingFlow: Flow<PagingData<Product>> = getPagingProductUseCase(orderBy, ascending)
                    .cachedIn(viewModelScope)

                currentProductFlow = pagingFlow

                // Wrap the PagingData in a Flow using flowOf
                _shoppingUiState.update { ShoppingUiState.Success(pagingFlow) }

            } catch (e: Exception) {
                _shoppingUiState.update { ShoppingUiState.Error(e) }
            }
        }
    }

    fun getProductPagingFlow(): Flow<PagingData<Product>>? {
        return currentProductFlow
    }

    fun refreshProducts() {
        loadProducts()
    }

    fun sortProducts(orderBy: String, ascending: Boolean) {
        if (orderBy != currentOrderBy || ascending != currentAscending) {
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