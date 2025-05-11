package com.fajarxfce.feature.home.ui

import androidx.lifecycle.ViewModel
import com.fajarxfce.core.ui.delegate.mvi.MVI
import com.fajarxfce.core.ui.delegate.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import com.fajarxfce.feature.home.ui.HomeContract.UiAction
import com.fajarxfce.feature.home.ui.HomeContract.UiEffect
@HiltViewModel
internal class HomeViewModel @Inject constructor(

) : ViewModel(), MVI<HomeContract.UiState, UiAction, UiEffect> by mvi(
    HomeContract.UiState())
{
    override fun onAction(action: UiAction) {

    }
}