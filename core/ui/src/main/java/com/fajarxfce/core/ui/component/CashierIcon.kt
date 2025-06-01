package com.fajarxfce.core.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
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

@Preview
@Composable
fun CashierIconFromImageVectorPreview() {
    CashierIcon(imageVector = Icons.Default.Home)
}

@Preview
@Composable
fun CashierIconFromPainterPreview() {
    CashierIcon(
        painter = androidx.compose.ui.graphics.painter.ColorPainter(
            Color.Red
        )
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