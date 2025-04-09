package com.fajarxfce.core.designsystem.theme

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.serialization.Serializable

private val darkColorScheme = darkColorScheme(
    primary = dark_primary,
    primaryContainer = dark_primaryContainer,
    onPrimaryContainer = dark_onPrimaryContainer,
    background = dark_background,
    onBackground = dark_onBackground,
    surface = dark_surface,
    onSurface = dark_onSurface,
    onSurfaceVariant = dark_onSurfaceVariant
)


@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Suppress("DEPRECATION")
@Composable
fun AppTheme(
    navController: NavHostController = rememberNavController(),
    systemUiController: SystemUiController = rememberSystemUiController(),
    content: @Composable () -> Unit
) {
    LaunchedEffect(systemUiController) {
        systemUiController.setStatusBarColor(
            color = darkColorScheme.background,
            darkIcons = false
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(navBackStackEntry) {
        val currentRoute = navBackStackEntry?.destination?.route
        Log.d("TAG", "AppThemeBjir: ${currentRoute?.substringAfterLast(".")}")
        val routeName = currentRoute?.substringAfterLast(".")
        val inNavigationBarScreen =
                routeName == "SplashRoute" || routeName == "OnBoardingRoute" || routeName == "MainRoute"

        val gestureBarColor = if (inNavigationBarScreen) {
            darkColorScheme.primaryContainer
        } else {
            darkColorScheme.background
        }

        systemUiController.setNavigationBarColor(
            color = gestureBarColor,
            darkIcons = false
        )
    }

    MaterialTheme(
        colorScheme = darkColorScheme,
        shapes = AppShapes,
        typography = AppTypography,
        content = content
    )
}

@Serializable data object SplashBaseRoute
@Serializable data object OnboardingBaseRoute
@Serializable data object MainBaseRoute