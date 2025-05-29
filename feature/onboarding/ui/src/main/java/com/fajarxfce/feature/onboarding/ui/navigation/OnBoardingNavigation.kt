package com.fajarxfce.feature.onboarding.ui.navigation

import androidx.annotation.Keep
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fajarxfce.core.ui.navigation.Screen
import com.fajarxfce.feature.onboarding.ui.OnBoardingScreen
import com.fajarxfce.feature.onboarding.ui.OnBoardingViewModel
import kotlinx.serialization.Serializable
@Keep
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
            onNavigateToLogin = onNavigateToLogin,
        )
    }
}