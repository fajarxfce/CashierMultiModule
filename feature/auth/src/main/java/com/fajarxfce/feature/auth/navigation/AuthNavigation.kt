package com.fajarxfce.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fajarxfce.feature.auth.ui.LoginScreen
import com.fajarxfce.feature.auth.ui.RegisterScreen
import kotlinx.serialization.Serializable

@Serializable data object AuthBaseRoute
@Serializable data object LoginRoute

@Serializable data object RegisterRoute

fun NavController.navigateToAuth() {
    navigate(AuthBaseRoute) {
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToLogin() {
    navigate(LoginRoute) {
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToRegister() {
    navigate(RegisterRoute) {
        launchSingleTop = true
        restoreState = true
    }
}

fun NavGraphBuilder.authSection(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    navigation<AuthBaseRoute>(
        startDestination = LoginRoute
    ) {
        composable<LoginRoute> {
            LoginScreen(
                onLoginClick = onLoginSuccess,
                onSignUpClick = onNavigateToRegister,
                onForgotPasswordClick = {}
            )
        }
        composable<RegisterRoute> {
            RegisterScreen(
                onRegisterSuccess = {},
                onNavigateToLogin = {},
            )
        }
    }
}
