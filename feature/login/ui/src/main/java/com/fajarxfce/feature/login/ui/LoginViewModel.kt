package com.fajarxfce.feature.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajarxfce.core.ui.component.DialogState
import com.fajarxfce.core.ui.delegate.mvi.MVI
import com.fajarxfce.core.ui.delegate.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(

) : ViewModel(),
    MVI<LoginContract.UiState, LoginContract.UiAction, LoginContract.UiEffect> by mvi(initialState = LoginContract.UiState()) {
    override fun onAction(action: LoginContract.UiAction) {
        viewModelScope.launch {
            when (action) {
                is LoginContract.UiAction.OnLoginClick -> login()

                is LoginContract.UiAction.OnRegisterClick -> {
                    // Handle register action
                }

                is LoginContract.UiAction.OnBackClick -> {
                    // Handle navigate back action
                }

                is LoginContract.UiAction.OnDialogDismiss -> updateUiState { copy(dialogState = null) }

                is LoginContract.UiAction.OnForgotPasswordSheetDismiss -> {
                    // Handle error confirm action
                }

                is LoginContract.UiAction.OnForgotPasswordClick -> {
                    // Handle forgot password action
                }

                is LoginContract.UiAction.OnEmailChange -> updateUiState { copy(email = action.email) }

                is LoginContract.UiAction.OnSendPasswordResetEmailClick -> {
                    // Handle password change action
                }

                is LoginContract.UiAction.OnPasswordChange -> updateUiState { copy(password = action.password) }
            }
        }
    }

    private fun login() =
        viewModelScope.launch {
            updateUiState { copy(isLoading = true) }
            delay(3000)
            updateUiState {
                copy(
                    isLoading = false,
                    dialogState = DialogState(
                        message = "Login Success",
                        isSuccess = true,
                    ),
                )
            }
        }

}