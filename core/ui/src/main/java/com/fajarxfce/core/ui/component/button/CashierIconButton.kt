package com.fajarxfce.core.ui.component.button

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.fajarxfce.core.ui.theme.CashierGray

@Composable
fun CashierIconButton(imageVector: ImageVector, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "",
            tint = CashierGray,
        )
    }

}

@Preview
@Composable
private fun CashierIconButtonPreview() {
    CashierIconButton(
        imageVector = ImageVector.vectorResource(id = com.fajarxfce.core.ui.R.drawable.core_ui_ic_arrow_left),
        onClick = {}
    )
}