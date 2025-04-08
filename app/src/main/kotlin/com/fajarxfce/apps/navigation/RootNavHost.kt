package com.fajarxfce.apps.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.fajarxfce.feature.main.navigation.MainRoute
import com.fajarxfce.feature.main.navigation.mainSection
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
            onSplashFinished = {
                with(navController) {
                    navigate(OnBoardingRoute) {
                        popUpTo(SplashBaseRoute) {
                            inclusive = true
                        }
                    }
                }
            }
        )

        onBoardingSection(
            onFinished = {
                with(navController) {
                    navigate(MainRoute){
                        popUpTo(OnBoardingRoute) {
                            inclusive = true
                        }
                    }
                }
            }
        )

        mainSection()
    }
}