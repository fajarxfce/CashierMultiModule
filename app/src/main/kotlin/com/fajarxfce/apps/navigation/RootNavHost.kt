package com.fajarxfce.apps.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.fajarxfce.core.model.data.UserData
import com.fajarxfce.feature.auth.navigation.AuthBaseRoute
import com.fajarxfce.feature.auth.navigation.authSection
import com.fajarxfce.feature.auth.navigation.navigateToRegister
import com.fajarxfce.feature.main.navigation.MainRoute
import com.fajarxfce.feature.main.navigation.mainSection
import com.fajarxfce.feature.main.navigation.navigateToMain
import com.fajarxfce.feature.onboarding.navigation.OnBoardingRoute
import com.fajarxfce.feature.onboarding.navigation.onBoardingSection
import com.fajarxfce.feature.splash.navigation.SplashBaseRoute
import com.fajarxfce.feature.splash.navigation.splashSection

@Composable
fun RootNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = SplashBaseRoute,
    ) {
        splashSection(
            onNavigateToOnboarding = {
                with(navController) {
                    navigate(OnBoardingRoute) {
                        popUpTo(SplashBaseRoute) {
                            inclusive = true
                        }
                    }
                }
            },
            onNavigateToMain = {
                with(navController) {
                    navigate(MainRoute) {
                        popUpTo(SplashBaseRoute) {
                            inclusive = true
                        }
                    }
                }
            },
        )

        onBoardingSection(
            onFinished = {
                with(navController) {
                    navigate(AuthBaseRoute) {
                        popUpTo(OnBoardingRoute) {
                            inclusive = true
                        }
                    }
                }
            },
        )

        authSection(
            onLoginSuccess = {
                with(navController) {
                    navigate(MainRoute) {
                        popUpTo(AuthBaseRoute) {
                            inclusive = true
                        }
                    }
                }
            },
            onNavigateToRegister = navController::navigateToRegister,
        )

        mainSection()
    }
}