package com.fajarxfce.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightThemeColors = lightColorScheme(
    primary = CashierBlue,
    secondary = CashierBlue,
    surface = Color.White,
    onSurface = Color.White,
    onBackground = Color.Black
)

private val DarkThemeColors = darkColorScheme(
    primary = CashierBlue,
    secondary = CashierBlue,
    surface = Color.Black,
    onSurface = CashierGray,
    onBackground = Color.White,
    background = CashierBackground
)
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkThemeColors else LightThemeColors,
        shapes = AppShapes,
        content = content
    )
}
