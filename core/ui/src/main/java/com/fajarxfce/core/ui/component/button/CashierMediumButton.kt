package com.fajarxfce.core.ui.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fajarxfce.core.ui.component.CashierText
import com.fajarxfce.core.ui.theme.AppTheme

@Composable
fun CashierMediumButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    backgroundColor: ButtonColors = ButtonDefaults.buttonColors(
        contentColor = MaterialTheme.colorScheme.background,
    ),
    textColor: Color = Color.White,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.background,
        ),
        shape = RoundedCornerShape(25.dp),
        enabled = enabled,
    ) {
        CashierText(
            text = text,
            color = textColor,
            modifier = Modifier
                .padding(
                    top = 4.dp,
                    bottom = 4.dp,
                ),
        )
    }
}

@Preview
@Composable
fun CashierMediumButtonPreview() {
    AppTheme {
        CashierMediumButton(
            text = "Button",
        ) {}
    }
}