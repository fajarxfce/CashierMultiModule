package com.fajarxfce.core.ui.component

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import com.fajarxfce.core.ui.theme.CashierBlue

@Composable
fun CashierIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    tint: Color = CashierBlue
) {
    Icon(
        modifier = modifier,
        imageVector = imageVector,
        contentDescription = "",
        tint = tint
    )
}

@Composable
fun CashierIcon(modifier: Modifier = Modifier, painter: Painter, tint: Color = CashierBlue) {
    Icon(
        modifier = modifier,
        painter = painter,
        contentDescription = "",
        tint = tint
    )
}