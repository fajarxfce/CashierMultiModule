package com.fajarxfce.feature.pos.ui.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fajarxfce.feature.pos.ui.PosScreen
import com.fajarxfce.feature.pos.ui.PosViewModel
import kotlinx.serialization.Serializable

@Serializable data object Pos

fun NavGraphBuilder.posScreen(
    onNavigateBack: () -> Unit,
    onNavigateDetail: (Int) -> Unit,
){
    composable<Pos> {
        val viewModel = hiltViewModel<PosViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val uiEffect = viewModel.uiEffect
        val onAction = viewModel::onAction
        PosScreen(
            onNavigateBack = onNavigateBack,
            onNavigateDetail = onNavigateDetail,
            uiState = uiState,
            uiEffect = uiEffect,
            onAction = onAction,
        )
    }
}