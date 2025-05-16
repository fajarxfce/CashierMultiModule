package com.fajarxfce.feature.splash.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajarxfce.core.result.fold
import com.fajarxfce.core.ui.delegate.mvi.MVI
import com.fajarxfce.core.ui.delegate.mvi.mvi
import com.fajarxfce.feature.splash.domain.usecase.CheckUserLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkUserLoggedInUseCase: CheckUserLoggedInUseCase
) : ViewModel(), MVI<Unit, Unit, SplashContract.UiEffect> by mvi(initialState = Unit) {
    init {
        checkLoginUser()
    }
    private fun checkLoginUser() = viewModelScope.launch {
        checkUserLoggedInUseCase().fold(
            onSuccess = { emitUiEffect(SplashContract.UiEffect.NavigateHome) },
            onFailure = { emitUiEffect(SplashContract.UiEffect.NavigateWelcome) }
        )
    }
}