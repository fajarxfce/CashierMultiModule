package com.fajarxfce.feature.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fajarxfce.feature.splash.ui.SplashScreen
import kotlinx.serialization.Serializable

@Serializable data object SplashBaseRoute
@Serializable data object SplashRoute

fun NavGraphBuilder.splashSection(
    onNavigateToMain: () -> Unit,
    onNavigateToOnboarding: () -> Unit,
) {
    navigation<SplashBaseRoute>(
        startDestination = SplashRoute,
    ) {
        composable<SplashRoute> {
            SplashScreen(
                onNavigateToMain = onNavigateToMain,
                onNavigateToOnboarding = onNavigateToOnboarding,
            )
        }
    }
}