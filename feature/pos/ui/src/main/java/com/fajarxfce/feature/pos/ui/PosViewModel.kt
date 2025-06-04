package com.fajarxfce.feature.pos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fajarxfce.core.result.onFailure
import com.fajarxfce.core.result.onSuccess
import com.fajarxfce.core.ui.delegate.mvi.MVI
import com.fajarxfce.core.ui.delegate.mvi.mvi
import com.fajarxfce.feature.pos.domain.model.Product
import com.fajarxfce.feature.pos.domain.model.toCart
import com.fajarxfce.feature.pos.domain.params.UpsertProductToCartParam
import com.fajarxfce.feature.pos.domain.usecase.GetCategoryByQueryUseCase
import com.fajarxfce.feature.pos.domain.usecase.GetProductMerkByQueryUseCase
import com.fajarxfce.feature.pos.domain.usecase.GetProductPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
internal class PosViewModel @Inject constructor(
    private val getProductPagingUseCase: GetProductPagingUseCase,
    private val getCategoryByQueryUseCase: GetCategoryByQueryUseCase,
    private val getProductMerkByQueryUseCase: GetProductMerkByQueryUseCase,
) : ViewModel(),
    MVI<PosContract.UiState, PosContract.UiAction, PosContract.UiEffect> by mvi(
        initialState = PosContract.UiState(
            productsFlow = emptyFlow(),
        )
    ) {

        init {
            val initialSearchQuery = uiState.value.searchQuery.value
            val initialParams = uiState.value.params.copy(search = initialSearchQuery.ifBlank { null })
            onAction(PosContract.UiAction.LoadProducts(initialParams))

            onAction(PosContract.UiAction.LoadTotalCartItem)
        }

    override fun onAction(uiAction: PosContract.UiAction) {
        viewModelScope.launch {
            when (uiAction) {
                is PosContract.UiAction.LoadProducts -> {
                    viewModelScope.launch {
                        val productsFlow = getProductPagingUseCase(
                            params = uiAction.params
                        ).cachedIn(viewModelScope)
                        updateUiState { copy(productsFlow = productsFlow) }
                    }
                }
                is PosContract.UiAction.OnProductItemClick -> {
                    updateUiState { copy(productForSheet = uiAction.product, isLoading = false) }
                    viewModelScope.launch {
                        emitUiEffect(PosContract.UiEffect.ShowProductDetailsSheet)
                    }
                }
                is PosContract.UiAction.AddToCartFromDetail -> {
                    handleAddToCart(
                        product = uiAction.product,
                        quantitySelected = uiAction.quantitySelected
                    )
                }

                PosContract.UiAction.OnDismissProductDetailsSheet -> {
                    updateUiState { copy(productForSheet = null) }
                }

                PosContract.UiAction.LoadTotalCartItem -> {
                    viewModelScope.launch {

                    }
                }

                is PosContract.UiAction.LoadCategory -> {
                    viewModelScope.launch {
                        val categoryFlow = getCategoryByQueryUseCase(
                            params = uiAction.params
                        ).cachedIn(viewModelScope)
                        updateUiState { copy(categoryFlow = categoryFlow) }
                    }
                }

                is PosContract.UiAction.LoadProductMerk -> {
                    viewModelScope.launch {
                        val productMerkFlow = getProductMerkByQueryUseCase(
                            params = uiAction.params
                        ).cachedIn(viewModelScope)
                        updateUiState { copy(productMerkFlow = productMerkFlow) }
                    }
                }

                PosContract.UiAction.OpenFilterSheet -> {
                    updateUiState {
                        copy(
                            tempSelectedCategoryIdsInSheet = params.productCategoryId,
                            tempSelectedMerkIdsInSheet = params.productMerkId
                        )
                    }
                }

                is PosContract.UiAction.ToggleCategoryFilterInSheet -> {
                    val currentSelection = uiState.value.tempSelectedCategoryIdsInSheet?.toMutableSet() ?: mutableSetOf()
                    if (currentSelection.contains(uiAction.categoryId)) {
                        currentSelection.remove(uiAction.categoryId)
                    } else {
                        currentSelection.add(uiAction.categoryId)
                    }
                    updateUiState { copy(tempSelectedCategoryIdsInSheet = currentSelection.toList().ifEmpty { null }) }
                }

                is PosContract.UiAction.ToggleMerkFilterInSheet -> {
                    val currentSelection = uiState.value.tempSelectedMerkIdsInSheet?.toMutableSet() ?: mutableSetOf()
                    if (currentSelection.contains(uiAction.merkId)) {
                        currentSelection.remove(uiAction.merkId)
                    } else {
                        currentSelection.add(uiAction.merkId)
                    }
                    updateUiState { copy(tempSelectedMerkIdsInSheet = currentSelection.toList().ifEmpty { null }) }
                }

                PosContract.UiAction.ResetTempFiltersInSheet -> {
                    updateUiState {
                        copy(
                            tempSelectedCategoryIdsInSheet = null,
                            tempSelectedMerkIdsInSheet = null
                        )
                    }
                }

                PosContract.UiAction.ApplyFiltersFromSheet -> {
                    val newParams = uiState.value.params.copy(
                        page = 1,
                        productCategoryId = uiState.value.tempSelectedCategoryIdsInSheet,
                        productMerkId = uiState.value.tempSelectedMerkIdsInSheet,

                        search = uiState.value.searchQuery.value.ifBlank { null }
                    )
                    updateUiState { copy(params = newParams) } // Update params aktif
                    onAction(PosContract.UiAction.LoadProducts(newParams))
                }
            }
        }
    }

    private fun handleAddToCart(product: Product, quantitySelected: Int){
        if (quantitySelected <= 0) {
            viewModelScope.launch {
                emitUiEffect(PosContract.UiEffect.ShowSnackbar("Quantity must be greater than 0"))
            }
            return
        }
        viewModelScope.launch {

        }
    }
}