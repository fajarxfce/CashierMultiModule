package com.fajarxfce.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

object CashierAppTheme {
    val colors: CashierAppColor
        @Composable
        @ReadOnlyComposable
        get() = if (isSystemInDarkTheme()) LocalDarkColors.current else LocalLightColors.current

    val icons: CashierAppIcons
        @Composable
        @ReadOnlyComposable
        get() = LocalIcons.current

    val typography: ESimTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}

@Composable
fun AppTheme(
    content: @Composable() () -> Unit
) {
    CompositionLocalProvider(
        LocalLightColors provides CashierAppTheme.colors,
        LocalIcons provides CashierAppTheme.icons,
        LocalTypography provides CashierAppTheme.typography,
    ) {
        content()
    }
}
