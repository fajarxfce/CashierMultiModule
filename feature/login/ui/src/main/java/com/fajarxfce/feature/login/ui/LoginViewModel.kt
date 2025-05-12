package com.fajarxfce.feature.login.ui

import androidx.lifecycle.ViewModel
import com.fajarxfce.core.ui.delegate.mvi.MVI
import com.fajarxfce.core.ui.delegate.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(

) : ViewModel(),
    MVI<LoginContract.UiState, LoginContract.UiAction, LoginContract.UiEffect> by mvi(initialState = LoginContract.UiState())
{
    override fun onAction(action: LoginContract.UiAction) {

    }
}