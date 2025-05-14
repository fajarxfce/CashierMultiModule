package com.fajarxfce.feature.pos.ui

internal object PosContract {
    data class UiState(
        val isLoading: Boolean = false,
        val searchText: String = "",
    )
    sealed interface UiAction {
        data object OnSearchClick : UiAction
        data object OnDetailClick : UiAction
        data object OnSearchTextChange : UiAction
    }
    sealed interface UiEffect {
        data class ShowError(val message: String) : UiEffect
        data object NavigateSearch : UiEffect
        data class NavigateDetail(val id: Int) : UiEffect
    }
}