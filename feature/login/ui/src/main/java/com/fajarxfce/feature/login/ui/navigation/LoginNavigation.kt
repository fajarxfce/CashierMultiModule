package com.fajarxfce.feature.login.ui.navigation

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
import com.fajarxfce.feature.login.ui.LoginScreen
import com.fajarxfce.feature.login.ui.LoginViewModel
import kotlinx.serialization.Serializable

@Serializable data object Login

fun NavGraphBuilder.loginScreen(
    onNavigateToHome: () -> Unit,
    onNavigateBack: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
) {
    composable<Login>(
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(500, easing = EaseInOutCubic)
            ) + fadeIn(animationSpec = tween(500))
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(500, easing = EaseInOutCubic)
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(500, easing = EaseInOutCubic)
            ) + fadeIn(animationSpec = tween(500))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(500, easing = EaseInOutCubic)
            ) + fadeOut(animationSpec = tween(300))
        }
    ) {
        val viewModel = hiltViewModel<LoginViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val uiEffect = viewModel.uiEffect
        LoginScreen(
            uiState = uiState,
            uiEffect = uiEffect,
            onAction = viewModel::onAction,
            onNavigateToHome = onNavigateToHome,
            onNavigateBack = onNavigateBack,
            onNavigateToRegister = onNavigateToRegister,
        )
    }
}