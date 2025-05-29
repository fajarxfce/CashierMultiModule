package com.fajarxfce.feature.pos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fajarxfce.core.ui.delegate.mvi.MVI
import com.fajarxfce.core.ui.delegate.mvi.mvi
import com.fajarxfce.feature.pos.domain.model.Product
import com.fajarxfce.feature.pos.domain.usecase.GetProductPagingUseCase
import com.fajarxfce.feature.pos.domain.usecase.InsertProductToCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
internal class PosViewModel @Inject constructor(
    private val getProductPagingUseCase: GetProductPagingUseCase,
    private val insertProductToCartUseCase: InsertProductToCartUseCase,
) : ViewModel(),
    MVI<PosContract.UiState, PosContract.UiAction, PosContract.UiEffect> by mvi(
        initialState = PosContract.UiState(
            productsFlow = emptyFlow()
        )
    ) {

    private val currentSearchQuery = MutableStateFlow<String?>(null)

    @OptIn(FlowPreview::class)
    private val productsBasedOnSearch: Flow<PagingData<Product>> = currentSearchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            getProductPagingUseCase(query = query).cachedIn(viewModelScope)
        }

    init {
        val initialProductsFlow = getProductPagingUseCase()
            .cachedIn(viewModelScope)

        initialProductsFlow
            .onEach { pagingData ->
                updateUiState { copy(productsFlow = flowOf(pagingData)) }
            }
            .launchIn(viewModelScope)

        productsBasedOnSearch
            .onEach { pagingData ->
                updateUiState { copy(productsFlow = flowOf(pagingData)) }
            }
            .launchIn(viewModelScope)
    }

    override fun onAction(uiAction: PosContract.UiAction) {
        viewModelScope.launch {
            when (uiAction) {
                is PosContract.UiAction.LoadProducts -> {}
                is PosContract.UiAction.OnProductItemClick -> {
                    viewModelScope.launch {
                        emitUiEffect(PosContract.UiEffect.ShowProductDetails(uiAction.product))
                    }
                }
                is PosContract.UiAction.OnAddToCartFromDetail -> {
                    insertProductToCart(uiAction.product, uiAction.quantity)
                }
            }
        }
    }

    private fun insertProductToCart(product: Product, quantity: Int) = viewModelScope.launch {
        insertProductToCartUseCase(product.id!!,quantity)
    }
}