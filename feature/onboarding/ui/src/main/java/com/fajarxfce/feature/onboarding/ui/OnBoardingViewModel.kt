package com.fajarxfce.feature.onboarding.ui

import androidx.lifecycle.ViewModel
import com.fajarxfce.core.ui.delegate.mvi.MVI
import com.fajarxfce.core.ui.delegate.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
internal class OnBoardingViewModel @Inject constructor(

) : ViewModel(),
    MVI<OnBoardingContract.UiState, OnBoardingContract.UiAction, OnBoardingContract.UiEffect> by mvi(initialState = OnBoardingContract.UiState())
{
    override fun onAction(action: OnBoardingContract.UiAction) {

    }
}