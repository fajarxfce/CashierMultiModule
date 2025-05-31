package com.fajarxfce.feature.cart.ui.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fajarxfce.feature.cart.ui.CartScreen
import com.fajarxfce.feature.cart.ui.CartViewModel
import kotlinx.serialization.Serializable

@Serializable data object Cart

fun NavGraphBuilder.cartScreen(
    onNavigateBack: () -> Unit,
){
    composable<Cart> {
        val viewModel = hiltViewModel<CartViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val uiEffect = viewModel.uiEffect
        val onAction = viewModel::onAction

        CartScreen(
            uiState = uiState,
            uiEffect = uiEffect,
            uiAction = onAction,
            onNavigateBack = onNavigateBack
        )
    }
}