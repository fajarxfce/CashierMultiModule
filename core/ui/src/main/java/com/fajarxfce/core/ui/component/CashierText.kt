package com.fajarxfce.core.ui.component


import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.fajarxfce.core.ui.theme.CashierGray

@Composable
fun CashierText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 16.sp,
    color: Color = MaterialTheme.colorScheme.onBackground,
    style: TextStyle = LocalTextStyle.current,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize,
        color = color,
        style = style,
        textAlign = textAlign,
        maxLines = maxLines
    )
}

@Composable
fun CashierTextGray(modifier: Modifier = Modifier, text: String, color: Color = CashierGray) {
    CashierText(
        modifier = modifier,
        text = text,
        color = color
    )
}

@Composable
fun CashierTextH5Text(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = TextStyle.Default.copy(
        fontWeight = FontWeight.Bold
    )
) {
    CashierText(
        modifier = modifier,
        text = text,
        fontSize = 24.sp,
        style = style,
        textAlign = TextAlign.Center
    )
}

@Composable
fun CashierTextBody1(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 14.sp,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    CashierText(
        text = text,
        modifier = modifier,
        fontSize = fontSize,
        color = color
    )
}