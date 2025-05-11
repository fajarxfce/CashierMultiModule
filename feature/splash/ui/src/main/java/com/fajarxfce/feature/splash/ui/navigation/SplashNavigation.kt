package com.fajarxfce.feature.splash.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fajarxfce.core.ui.navigation.Screen
import kotlinx.serialization.Serializable

@Serializable data object Splash : Screen

fun NavGraphBuilder.splashScreen(
    onNavigateToWelcome: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    composable<Splash> {

    }
}