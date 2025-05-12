package com.fajarxfce.feature.onboarding.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajarxfce.core.ui.delegate.mvi.MVI
import com.fajarxfce.core.ui.delegate.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
internal class OnBoardingViewModel @Inject constructor(

) : ViewModel(),
    MVI<OnBoardingContract.UiState, OnBoardingContract.UiAction, OnBoardingContract.UiEffect> by mvi(initialState = OnBoardingContract.UiState())
{
    override fun onAction(action: OnBoardingContract.UiAction) {
        viewModelScope.launch {
            Log.d("TAG", "OnBoardingScreen: $action")
            when (action) {

                OnBoardingContract.UiAction.OnClickNext -> {}
                OnBoardingContract.UiAction.OnClickSkip -> {}
                OnBoardingContract.UiAction.OnClickDone -> {}
                OnBoardingContract.UiAction.OnClickDialogDismiss -> {}
            }
        }
    }
}