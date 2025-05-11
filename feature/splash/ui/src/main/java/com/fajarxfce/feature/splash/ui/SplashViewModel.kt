package com.fajarxfce.feature.splash.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajarxfce.core.ui.delegate.mvi.MVI
import com.fajarxfce.core.ui.delegate.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(

) : ViewModel(), MVI<Unit, Unit, SplashContract.UiEffect> by mvi(initialState = Unit) {
    init {
        checkLoginUser()
    }
    private fun checkLoginUser() = viewModelScope.launch {
        delay(2000)
        emitUiEffect(SplashContract.UiEffect.NavigateHome)
    }
}