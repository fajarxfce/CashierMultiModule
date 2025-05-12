package com.fajarxfce.feature.onboarding.ui

import com.fajarxfce.core.ui.component.DialogState

internal object OnBoardingContract {
    data class UiState(
        val isLoading: Boolean = false,
        val dialogState: DialogState? = null,
    )

    sealed interface UiAction {
        data object OnLoginWithGoogleClick : UiAction
        data object OnLoginWithEmailClick : UiAction
        data object OnSignUpClick : UiAction
        data object OnClickDialogDismiss : UiAction
    }

    sealed interface UiEffect {
        data object NavigateToEmailLogin : UiEffect
        data object NavigateToSignUp : UiEffect
        data object NavigateToGoogleLogin : UiEffect
        data object ShowDialog : UiEffect
    }

}