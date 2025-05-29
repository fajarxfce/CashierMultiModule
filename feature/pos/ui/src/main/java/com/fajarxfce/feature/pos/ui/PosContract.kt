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
        data object OnSearchClick : UiAction
         data class OnProductClick(val productId: Int) : UiAction
        data class OnSearchQueryChanged(val query: String) : UiAction
        data object RetryLoad : UiAction
    }
    sealed interface UiEffect {
        data class ShowErrorSnackbar(val message: String) : UiEffect
        data object NavigateToSearchScreen : UiEffect
        data class NavigateToProductDetail(val productId: Int) : UiEffect
    }
}