package com.fajarxfce.core.ui.theme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val greenMain = Color(0xFF4CAF50)
private val lightGreenMain = Color(0xFFE8F5E9)
private val teal = Color(0xFF009688)
private val lightTeal = Color(0xFFB2DFDB)
private val amber = Color(0xFFFFC107)
private val lightAmber = Color(0xFFFFECB3)

private val red = Color(0xFFF44336)
private val lightRed = Color(0xFFFDDEDE)

internal val LocalLightColors = staticCompositionLocalOf { lightColors() }
internal val LocalDarkColors = staticCompositionLocalOf { darkColors() }

internal fun darkColors(
    onBackground: Color = Color.White,
    background: Color = Color(0xFF121212),
    blue: Color = greenMain, // Mengganti blue ke greenMain
    lightBlue: Color = lightGreenMain.copy(alpha = 0.5f), // Mengganti lightBlue ke lightGreenMain
    yellow: Color = amber, // Mengganti yellow ke amber
    lightYellow: Color = lightAmber.copy(alpha = 0.5f), // Mengganti lightYellow ke lightAmber
    green: Color = teal,
    softGreen: Color = lightTeal.copy(alpha = 0.5f),
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
    background: Color = Color(0xFFF1F8E9), // Light green sangat muda
    onBackground: Color = Color(0xFF1A1A1A),
    blue: Color = greenMain,
    lightBlue: Color = lightGreenMain,
    yellow: Color = amber,
    lightYellow: Color = lightAmber,
    green: Color = teal,
    softGreen: Color = lightTeal,
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