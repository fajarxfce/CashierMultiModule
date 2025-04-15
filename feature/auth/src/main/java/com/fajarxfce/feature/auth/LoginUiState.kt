package com.fajarxfce.feature.auth

sealed class LoginUiState {
    data object Initial : LoginUiState()
    data object Loading : LoginUiState()
    data class Success(val user: Any? = null) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}