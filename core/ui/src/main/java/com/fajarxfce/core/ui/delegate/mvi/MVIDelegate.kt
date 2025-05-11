package com.fajarxfce.core.ui.delegate.mvi

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MVIDelegate<UiState, UIAction, UiEffect>(
    initialState: UiState,
) : MVI<UiState, UIAction, UiEffect> {

    private val _uiState by lazy { MutableStateFlow(initialState) }
    override val uiState: StateFlow<UiState>
        get() = TODO("Not yet implemented")

    private val _currentUiState by lazy { initialState }
    override val currentUiState: UiState
        get() = TODO("Not yet implemented")

    private val _uiEffect by lazy { Channel<UiEffect>() }
    override val uiEffect: Flow<UiEffect>
        get() = TODO("Not yet implemented")

    override fun onAction(action: UIAction) = Unit

    override fun updateState(block: UiState.() -> UiState) =
        _uiState.update(block)

    override suspend fun emitUiEffect(uiEffect: UiEffect) =
        _uiEffect.send(uiEffect)
}