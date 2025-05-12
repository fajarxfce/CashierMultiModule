package com.fajarxfce.feature.onboarding.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajarxfce.core.ui.delegate.mvi.MVI
import com.fajarxfce.core.ui.delegate.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
internal class OnBoardingViewModel @Inject constructor(

) : ViewModel(),
    MVI<OnBoardingContract.UiState, OnBoardingContract.UiAction, OnBoardingContract.UiEffect> by mvi(initialState = OnBoardingContract.UiState())
{
    override fun onAction(action: OnBoardingContract.UiAction) {
        viewModelScope.launch {
            when (action) {
                OnBoardingContract.UiAction.OnLoginWithEmailClick -> emitUiEffect(OnBoardingContract.UiEffect.NavigateToEmailLogin)
                OnBoardingContract.UiAction.OnLoginWithGoogleClick -> emitUiEffect(OnBoardingContract.UiEffect.NavigateToGoogleLogin)
                OnBoardingContract.UiAction.OnClickDialogDismiss -> {}
                OnBoardingContract.UiAction.OnSignUpClick -> {}
            }
        }
    }
}