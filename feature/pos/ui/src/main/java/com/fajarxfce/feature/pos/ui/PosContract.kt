package com.fajarxfce.feature.pos.ui

import androidx.paging.PagingData
import com.fajarxfce.feature.pos.domain.model.Category
import com.fajarxfce.feature.pos.domain.model.Product
import com.fajarxfce.feature.pos.domain.params.GetAllProductParams
import com.fajarxfce.feature.pos.domain.params.GetCategoryByQueryParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal object PosContract {
    data class FilterSelections(
        val selectedCategoryIds: List<Int> = emptyList(),
        val selectedBrandIds: List<Int> = emptyList()
    )
    data class UiState(
        val isLoading: Boolean = false,
        val productsFlow: Flow<PagingData<Product>> = emptyFlow(),
        val categoryFlow: Flow<PagingData<Category>> = emptyFlow(),
        val productForSheet: Product? = null,
        val totalCartItem: Int = 0,
        val params: GetAllProductParams = GetAllProductParams(page = 1),
        val categoryParams: GetCategoryByQueryParams = GetCategoryByQueryParams(page = 1),
    )

    sealed interface UiAction {
        data class LoadProducts(val params: GetAllProductParams) : UiAction
        data object LoadTotalCartItem : UiAction
        data class LoadCategory(val params: GetCategoryByQueryParams): UiAction
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