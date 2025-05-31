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
import com.fajarxfce.feature.pos.domain.usecase.GetProductPagingUseCase
import com.fajarxfce.feature.pos.domain.usecase.UpsertProductToCartUseCase
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
    private val upsertProductToCartUseCase: UpsertProductToCartUseCase,
) : ViewModel(),
    MVI<PosContract.UiState, PosContract.UiAction, PosContract.UiEffect> by mvi(
        initialState = PosContract.UiState(
            productsFlow = emptyFlow(),
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
            upsertProductToCartUseCase(param = UpsertProductToCartParam(
                productId = product.id,
                name = product.name,
                price = product.price,
                quantity = quantitySelected,
                imageUrl = product.imageUrl
            ))
                .onSuccess {
                    emitUiEffect(PosContract.UiEffect.ShowSnackbar("Product added to cart"))
                }
                .onFailure {
                    emitUiEffect(PosContract.UiEffect.ShowSnackbar(it.message.toString()))
                }
        }
    }
}