package com.fajarxfce.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
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
            onNavigateToWelcome = {  }
        )
    }
}