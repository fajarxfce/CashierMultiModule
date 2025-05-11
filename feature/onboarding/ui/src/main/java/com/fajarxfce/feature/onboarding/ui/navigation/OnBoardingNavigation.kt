package com.fajarxfce.feature.onboarding.ui.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fajarxfce.core.ui.navigation.Screen
import com.fajarxfce.feature.onboarding.ui.OnBoardingScreen
import com.fajarxfce.feature.onboarding.ui.OnBoardingViewModel
import kotlinx.serialization.Serializable

@Serializable data object OnBoarding : Screen

fun NavGraphBuilder.onBoardingScreen(
    onNavigateToLogin: () -> Unit,
) {
    composable<OnBoarding> {
        val viewModel = hiltViewModel<OnBoardingViewModel>()
        val uiEffect = viewModel.uiEffect
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        OnBoardingScreen(
            uiState = uiState,
            uiEffect = uiEffect,
            onAction = viewModel::onAction,
            onNavigateToLogin = {},
        )
    }
}