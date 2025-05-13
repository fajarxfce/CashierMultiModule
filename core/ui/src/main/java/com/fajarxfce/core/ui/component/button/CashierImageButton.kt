package com.fajarxfce.core.ui.component.button

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CashierImageButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contextDescription: String = "",
    onIconClick: () -> Unit
) {
    IconButton(
        modifier = modifier
            .padding(8.dp),
        onClick = onIconClick
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contextDescription
        )
    }
}

@Preview
@Composable
fun CashierImageButtonPreview() {
    CashierImageButton(
        imageVector = Icons.Default.Add,
        onIconClick = { }
    )
}

