package com.fajarxfce.feature.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.fajarxfce.core.data.domain.usecase.AuthUseCase
import com.fajarxfce.core.result.Result.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.Initial)
    val loginState: StateFlow<LoginUiState> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authUseCase.login(email, password).collect { result ->
                _loginState.value = when (result) {
                    is Loading -> LoginUiState.Loading
                    is Success -> LoginUiState.Success(result.data)
                    is Error -> LoginUiState.Error(
                        result.exception.message ?: "Authentication failed"
                    )
                }
            }
        }
    }

    fun resetState() {
        _loginState.value = LoginUiState.Initial
    }
}