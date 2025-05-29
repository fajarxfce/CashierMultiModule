package com.fajarxfce.feature.pos.ui.navigation

import androidx.annotation.Keep
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fajarxfce.core.ui.navigation.Screen
import com.fajarxfce.feature.pos.ui.PosScreen
import com.fajarxfce.feature.pos.ui.PosViewModel
import kotlinx.serialization.Serializable
@Keep
@Serializable data object Pos : Screen

fun NavGraphBuilder.posScreen(
    onNavigateBack: () -> Unit,
    onNavigateDetail: (Int) -> Unit,
){
    composable<Pos>{
        val viewModel = hiltViewModel<PosViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val uiEffect = viewModel.uiEffect
        val onAction = viewModel::onAction
        PosScreen(
            uiState = uiState,
            uiEffect = uiEffect,
            onAction = onAction,
            onNavigateBack = onNavigateBack,
        )
    }
}