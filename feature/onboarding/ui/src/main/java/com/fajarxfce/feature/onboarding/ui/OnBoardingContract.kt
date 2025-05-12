package com.fajarxfce.feature.onboarding.ui

import com.fajarxfce.core.ui.component.DialogState

internal object OnBoardingContract {
    data class UiState(
        val isLoading: Boolean = false,
        val dialogState: DialogState? = null,
    )

    sealed interface UiAction {
        data object OnClickNext : UiAction
        data object OnClickSkip : UiAction
        data object OnClickDone : UiAction
        data object OnClickDialogDismiss : UiAction
    }

    sealed interface UiEffect {
        data object NavigateLogin : UiEffect
        data object ShowDialog : UiEffect
    }

}