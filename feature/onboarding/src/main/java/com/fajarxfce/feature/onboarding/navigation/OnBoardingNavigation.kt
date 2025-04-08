package com.fajarxfce.feature.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fajarxfce.feature.onboarding.ui.OnBoardingScreen
import kotlinx.serialization.Serializable

@Serializable data object OnBoardingBaseRoute
@Serializable data object OnBoardingRoute

fun NavController.navigteToOnBoarding() {
    navigate(OnBoardingRoute) {
        popUpTo(OnBoardingBaseRoute) {
            inclusive = true
        }
    }
}

fun NavGraphBuilder.onBoardingSection(
    onFinished: () -> Unit,
) {
    navigation<OnBoardingBaseRoute>(
        startDestination = OnBoardingRoute,
    ) {
        composable<OnBoardingRoute> {
            OnBoardingScreen(
                onBoardingCompleted = onFinished
            )
        }
    }
}