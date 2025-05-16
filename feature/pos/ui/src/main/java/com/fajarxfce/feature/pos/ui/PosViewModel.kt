package com.fajarxfce.feature.pos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajarxfce.core.result.fold
import com.fajarxfce.core.ui.delegate.mvi.MVI
import com.fajarxfce.core.ui.delegate.mvi.mvi
import com.fajarxfce.feature.pos.domain.usecase.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PosViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel(), MVI<PosContract.UiState, PosContract.UiAction, PosContract.UiEffect> by mvi(initialState = PosContract.UiState())
{
    init {
        getAllProducts()
    }
    private fun getAllProducts() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }
        getAllProductsUseCase().fold(
            onSuccess = {
                updateUiState {
                    copy(
                        products = it,
                        isLoading = false,
                    )
                }
            },
            onFailure = {
                updateUiState {
                    copy(
                        isLoading = false
                    )
                }
            }
        )
    }
}