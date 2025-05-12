package com.fajarxfce.core.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Palet Warna Baru
private val red = Color(0xFFF44336) // Merah
private val redMain = Color(0xFFE57373) // Merah utama
private val lightRedMain = Color(0xFFFCE4EC)  // Merah muda
private val lightRed = Color(0xFFFFCDD2) // Merah muda lebih terang
private val pink = Color(0xFFF06292)
private val lightPink = Color(0xFFF8BBD0)
private val orange = Color(0xFFFF7043) // Mengganti amber ke orange
private val lightOrange = Color(0xFFFFCCBC) // Versi lebih terang dari orange


internal val LocalLightColors = staticCompositionLocalOf { lightColors() }
internal val LocalDarkColors = staticCompositionLocalOf { darkColors() }

internal fun darkColors(
    onBackground: Color = Color.White,
    background: Color = Color(0xFF121212),
    blue: Color = redMain, // Mengganti blue ke redMain
    lightBlue: Color = lightRedMain.copy(alpha = 0.5f), // Mengganti lightBlue ke lightRedMain
    yellow: Color = orange, // Mengganti yellow ke orange
    lightYellow: Color = lightOrange.copy(alpha = 0.5f), // Mengganti lightYellow ke lightOrange
    green: Color = pink,
    softGreen: Color = lightPink.copy(alpha = 0.5f),
    red: Color = com.fajarxfce.core.ui.theme.red,
    softRed: Color = lightRed.copy(alpha = 0.5f),
): CashierAppColor = CashierAppColor(
    background = background,
    onBackground = onBackground,
    blue = blue,
    lightBlue = lightBlue,
    yellow = yellow,
    lightYellow = lightYellow,
    green = green,
    softGreen = softGreen,
    red = red,
    softRed = softRed,
)

class CashierAppColor(
    background: Color,
    onBackground: Color,
    blue: Color,
    lightBlue: Color,
    yellow: Color,
    lightYellow: Color,
    green: Color,
    softGreen: Color,
    red: Color,
    softRed: Color,
) {
    private var _background: Color by mutableStateOf(background)
    val background: Color = _background

    private var _onBackground: Color by mutableStateOf(onBackground)
    val onBackground: Color = _onBackground

    private var _blue: Color by mutableStateOf(blue)
    val blue: Color = _blue

    private var _lightBlue: Color by mutableStateOf(lightBlue)
    val lightBlue: Color = _lightBlue

    private var _yellow: Color by mutableStateOf(yellow)
    val yellow: Color = _yellow

    private var _lightYellow: Color by mutableStateOf(lightYellow)
    val lightYellow: Color = _lightYellow

    private var _green: Color by mutableStateOf(green)
    val green: Color = _green

    private var _softGreen: Color by mutableStateOf(softGreen)
    val softGreen: Color = _softGreen

    private var _red: Color by mutableStateOf(red)
    val red: Color = _red

    private var _softRed: Color by mutableStateOf(softRed)
    val softRed: Color = _softRed
}

internal fun lightColors(
    background: Color = Color(0xFFFBE9E7), // Light red sangat muda
    onBackground: Color = Color(0xFF1A1A1A),
    blue: Color = redMain,
    lightBlue: Color = lightRedMain,
    yellow: Color = orange,
    lightYellow: Color = lightOrange,
    green: Color = pink,
    softGreen: Color = lightPink,
    red: Color = com.fajarxfce.core.ui.theme.red,
    softRed: Color = lightRed,
): CashierAppColor = CashierAppColor(
    background = background,
    onBackground = onBackground,
    blue = blue,
    lightBlue = lightBlue,
    yellow = yellow,
    lightYellow = lightYellow,
    green = green,
    softGreen = softGreen,
    red = red,
    softRed = softRed,
)