package com.fajarxfce.feature.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.fajarxfce.core.data.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.Initial)
    val loginState: StateFlow<LoginUiState> = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            authUseCase.login(username, password)
                .onStart {
                    _loginState.value = LoginUiState.Loading
                }
                .catch { exception ->
                    _loginState.value = LoginUiState.Error(exception.message ?: "Unknown error occurred")
                }
                .collect { result ->
                    Log.d("TAG", "login: ${result.name}")
                }
        }
    }

    fun resetState() {
        _loginState.value = LoginUiState.Initial
    }
}
