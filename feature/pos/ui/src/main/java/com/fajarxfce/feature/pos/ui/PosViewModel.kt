package com.fajarxfce.feature.pos.ui

import androidx.lifecycle.ViewModel
import com.fajarxfce.core.ui.delegate.mvi.MVI
import com.fajarxfce.core.ui.delegate.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PosViewModel @Inject constructor(

) : ViewModel(), MVI<PosContract.UiState, PosContract.UiAction, PosContract.UiEffect> by mvi(initialState = PosContract.UiState())
{

}