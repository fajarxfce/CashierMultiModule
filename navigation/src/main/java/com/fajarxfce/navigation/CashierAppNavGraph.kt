package com.fajarxfce.navigation

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.fajarxfce.feature.login.ui.navigation.Login
import com.fajarxfce.feature.login.ui.navigation.loginScreen
import com.fajarxfce.feature.onboarding.ui.navigation.OnBoarding
import com.fajarxfce.feature.onboarding.ui.navigation.onBoardingScreen
import com.fajarxfce.feature.splash.ui.navigation.Splash
import com.fajarxfce.feature.splash.ui.navigation.splashScreen

@Composable
fun CashierAppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Splash,
        modifier = modifier,
    ) {
        splashScreen(
            onNavigateToHome = { },
            onNavigateToWelcome = {
                navController.apply {
                    navigate(OnBoarding) {
                        popUpTo(Splash) {
                            inclusive = true
                        }
                    }
                }
            }
        )
        onBoardingScreen(
            onNavigateToLogin = {
                navController.apply {
                    navigate(Login)
                }
            },
        )
        loginScreen(
            onNavigateToHome = {},
            onNavigateBack = {
                navController.popBackStack()
            },
            onNavigateToRegister = {},
        )
    }
}