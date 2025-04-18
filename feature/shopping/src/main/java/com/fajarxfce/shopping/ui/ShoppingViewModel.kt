package com.fajarxfce.shopping.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajarxfce.core.domain.usecase.product.GetAllProductUseCase
import com.fajarxfce.core.model.data.product.Product
import com.fajarxfce.core.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val getAllProductUseCase: GetAllProductUseCase
) : ViewModel() {

    private val _shoppingUiState = MutableStateFlow<ShoppingUiState<List<Product>>>(ShoppingUiState.Loading)
    val shoppingUiState: StateFlow<ShoppingUiState<List<Product>>>
        get() = _shoppingUiState.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            getAllProductUseCase().collect { result ->
                when (result) {
                    is Result.Loading -> _shoppingUiState.update { ShoppingUiState.Loading }
                    is Result.Success -> _shoppingUiState.update { ShoppingUiState.Success(result.data) }
                    is Result.Error -> _shoppingUiState.update { ShoppingUiState.Error(result.exception) }
                }
            }
        }
    }

    fun refreshProducts() {  // New function to trigger refresh
        loadProducts()
    }
}