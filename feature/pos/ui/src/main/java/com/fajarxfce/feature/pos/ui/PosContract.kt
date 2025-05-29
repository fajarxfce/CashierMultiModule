package com.fajarxfce.feature.pos.ui

import androidx.paging.PagingData
import com.fajarxfce.feature.pos.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal object PosContract {
    data class UiState(
        val isLoading: Boolean = false,
        val searchText: String = "",
        val productsFlow: Flow<PagingData<Product>> = emptyFlow(),
    )
    sealed interface UiAction {
        data object LoadProducts : UiAction
        data class OnProductItemClick(val product: Product) : UiAction
        data class OnAddToCartFromDetail(val product: Product, val quantity: Int) : UiAction
    }
    sealed interface UiEffect {
        data class ShowProductDetails(val product: Product) : UiEffect
        data object HideProductDetailsSheet : UiEffect
        data class ShowSnackbar(val message: String, val isError: Boolean = false) : UiEffect
    }
}