package com.fajarxfce.feature.pos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fajarxfce.core.ui.delegate.mvi.MVI
import com.fajarxfce.core.ui.delegate.mvi.mvi
import com.fajarxfce.feature.pos.domain.model.Product
import com.fajarxfce.feature.pos.domain.usecase.GetProductPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
internal class PosViewModel @Inject constructor(
    private val getProductPagingUseCase: GetProductPagingUseCase
) : ViewModel(),
    MVI<PosContract.UiState, PosContract.UiAction, PosContract.UiEffect> by mvi(
        initialState = PosContract.UiState(
            // Biarkan kosong atau dengan emptyFlow() di sini
            productsFlow = emptyFlow()
        )
    ) {

    private val currentSearchQuery = MutableStateFlow<String?>(null)

    // Flow untuk pencarian (jika ada)
    @OptIn(FlowPreview::class)
    private val productsBasedOnSearch: Flow<PagingData<Product>> = currentSearchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            getProductPagingUseCase(query = query).cachedIn(viewModelScope) // cache di sini
        }
    // Tidak perlu .cachedIn(viewModelScope) lagi di sini jika sudah di flatMapLatest

    init {
        // Aliran utama data produk
        val initialProductsFlow = getProductPagingUseCase() // Tanpa query awal
            .cachedIn(viewModelScope) // Panggil cachedIn di sini

        // Update UiState dengan flow awal
        initialProductsFlow
            .onEach { pagingData ->
                Timber.d("PagingData received: $pagingData")
                updateUiState { copy(productsFlow = flowOf(pagingData)) }
            }
            .launchIn(viewModelScope) // Atau langsung assign jika UiState.productsFlow adalah Flow<PagingData<Product>>

        // Jika Anda ingin productsBasedOnSearch juga mengupdate UiState.productsFlow
        productsBasedOnSearch
            .onEach { pagingData ->
                Timber.d("PagingData received: $pagingData")
                updateUiState { copy(productsFlow = flowOf(pagingData)) }
            }
            .launchIn(viewModelScope)
    }

    override fun onAction(uiAction: PosContract.UiAction) {
        when (uiAction) {
            PosContract.UiAction.OnSearchClick -> { /* ... */ }
            is PosContract.UiAction.OnSearchQueryChanged -> {
                updateUiState { copy(searchText = uiAction.query) }
                currentSearchQuery.value = uiAction.query.takeIf { it.isNotBlank() }
            }
            PosContract.UiAction.RetryLoad -> { /* ... */ }
            is PosContract.UiAction.OnProductClick -> {}
        }
    }
}