package com.fajarxfce.shopping.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajarxfce.core.data.domain.usecase.product.GetProductUseCase
import com.fajarxfce.core.model.data.product.Product
import com.fajarxfce.core.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase
) : ViewModel() {

    private val _shoppingUiState = MutableStateFlow<ShoppingUiState<List<Product>>>(ShoppingUiState.Loading)
    val shoppingUiState: StateFlow<ShoppingUiState<List<Product>>>
        get() = _shoppingUiState

    fun getProducts() {
        viewModelScope.launch {
            getProductUseCase.getAllProduct().collect { result ->
                Timber.d("getProducts: $result")
                when (result) {
                    is Result.Loading -> {
                        _shoppingUiState.update { ShoppingUiState.Loading }
                    }

                    is Result.Success -> {
                        _shoppingUiState.update { ShoppingUiState.Success(result.data) }
                    }

                    is Result.Error -> {
                        _shoppingUiState.update { ShoppingUiState.Error(result.exception) }
                    }
                }
            }
        }
    }
}