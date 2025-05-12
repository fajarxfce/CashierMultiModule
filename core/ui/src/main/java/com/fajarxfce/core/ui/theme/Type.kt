package com.fajarxfce.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import com.fajarxfce.core.ui.R

internal val LocalTypography = staticCompositionLocalOf { ESimTypography() }

val Poppins = FontFamily(
    Font(R.font.core_ui_poppins_regular, FontWeight.Normal),
    Font(R.font.core_ui_poppins_medium, FontWeight.Medium),
    Font(R.font.core_ui_poppins_semi_bold, FontWeight.SemiBold),
    Font(R.font.core_ui_poppins_bold, FontWeight.Bold),
)

class ESimTypography {
    val heading1: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = Poppins,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None,
            ),
            color = CashierAppTheme.colors.onBackground
        )
    val heading2: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = Poppins,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None,
            ),
            color = CashierAppTheme.colors.onBackground
        )
    val heading3: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = Poppins,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None,
            ),
            color = CashierAppTheme.colors.onBackground
        )
    val heading4: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = Poppins,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None,
            ),
            color = CashierAppTheme.colors.onBackground
        )
    val heading5: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = Poppins,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None,
            ),
            color = CashierAppTheme.colors.onBackground
        )
    val heading6: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = Poppins,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None,
            ),
            color = CashierAppTheme.colors.onBackground
        )
    val heading7: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = Poppins,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None,
            ),
            color = CashierAppTheme.colors.onBackground
        )
    val subheading1: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = Poppins,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None,
            ),
            color = CashierAppTheme.colors.onBackground
        )
    val subheading2: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = Poppins,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None,
            ),
            color = CashierAppTheme.colors.onBackground
        )
    val subheading3: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = Poppins,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None,
            ),
            color = CashierAppTheme.colors.onBackground
        )
    val paragraph1: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = Poppins,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None,
            ),
            color = CashierAppTheme.colors.onBackground
        )
    val paragraph2: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = Poppins,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None,
            ),
            color = CashierAppTheme.colors.onBackground
        )
    val paragraph3: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = Poppins,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None,
            ),
            color = CashierAppTheme.colors.onBackground
        )
    val paragraph4: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = Poppins,
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None,
            ),
            color = CashierAppTheme.colors.onBackground
        )
}