package com.fajarxfce.feature.cart.ui

import androidx.lifecycle.ViewModel
import com.fajarxfce.core.ui.delegate.mvi.MVI
import com.fajarxfce.core.ui.delegate.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(

) : ViewModel(),
    MVI<CartContract.UiState, CartContract.UiAction, CartContract.UiEffect> by mvi(initialState = CartContract.UiState()) {

    override fun onAction(action: CartContract.UiAction) {

    }
}