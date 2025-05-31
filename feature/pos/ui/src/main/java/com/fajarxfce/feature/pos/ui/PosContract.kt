package com.fajarxfce.feature.pos.ui

import androidx.paging.PagingData
import com.fajarxfce.feature.pos.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal object PosContract {

    data class UiState(
        val isLoading: Boolean = false,
        val productsFlow: Flow<PagingData<Product>> = emptyFlow(),
        val productForSheet: Product? = null
    )

    sealed interface UiAction {
        data object LoadProducts : UiAction
        data class OnProductItemClick(val product: Product) : UiAction

        data class AddToCartFromDetail(val product: Product, val quantitySelected: Int) : UiAction

        data object OnDismissProductDetailsSheet : UiAction
    }

    sealed interface UiEffect {
        data object ShowProductDetailsSheet : UiEffect
        data object HideProductDetailsSheet : UiEffect
        data class ShowSnackbar(val message: String, val isError: Boolean = false) : UiEffect
    }
}