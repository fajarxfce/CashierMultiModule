package com.fajarxfce.feature.splash.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fajarxfce.core.ui.navigation.Screen
import com.fajarxfce.feature.splash.ui.SplashScreen
import com.fajarxfce.feature.splash.ui.SplashViewModel
import kotlinx.serialization.Serializable

@Serializable data object Splash : Screen

fun NavGraphBuilder.splashScreen(
    onNavigateToWelcome: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    composable<Splash> {
        val viewModel = hiltViewModel<SplashViewModel>()
        val uiEffect = viewModel.uiEffect
        SplashScreen(
            uiEffect = uiEffect,
            onNavigateToHome = onNavigateToHome,
            onNavigateToWelcome = onNavigateToWelcome,
        )
    }
}