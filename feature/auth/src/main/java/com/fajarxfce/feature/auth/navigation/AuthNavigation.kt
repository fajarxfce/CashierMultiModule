package com.fajarxfce.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fajarxfce.feature.auth.ui.LoginScreen
import kotlinx.serialization.Serializable

@Serializable data object AuthBaseRoute
@Serializable data object LoginRoute

fun NavController.navigateToAuth() {
    navigate(AuthBaseRoute) {
        launchSingleTop = true
        restoreState = true
    }
}

fun NavGraphBuilder.authSection(
    onLoginSuccess: () -> Unit
) {
    navigation<AuthBaseRoute>(
        startDestination = LoginRoute
    ) {
        composable<LoginRoute> {
            LoginScreen(
                onLoginClick = {username, password ->
                    onLoginSuccess
                },
                onSignUpClick = {},
                onForgotPasswordClick = {}
            )
        }
    }
}
