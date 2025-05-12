package com.fajarxfce.core.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajarxfce.core.ui.theme.AppTheme
import com.fajarxfce.core.ui.theme.CashierAppTheme

enum class CashierButtonType { PRIMARY, SECONDARY }
enum class CashierButtonSize { EXTRA_SMALL, SMALL, MEDIUM, LARGE }

@Composable
fun CashierButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean = true,
    type: CashierButtonType = CashierButtonType.PRIMARY,
    size: CashierButtonSize = CashierButtonSize.MEDIUM,
    icon: ImageVector? = null,
    onClick: () -> Unit
) {
    val textStyle = when(size){
        CashierButtonSize.EXTRA_SMALL -> CashierAppTheme.typography.subheading3
        CashierButtonSize.SMALL -> CashierAppTheme.typography.heading6
        CashierButtonSize.MEDIUM -> CashierAppTheme.typography.heading5
        CashierButtonSize.LARGE -> CashierAppTheme.typography.heading4
    }

    val height = when(size) {
        CashierButtonSize.EXTRA_SMALL -> 34.dp
        CashierButtonSize.SMALL -> 53.dp
        CashierButtonSize.MEDIUM -> 56.dp
        CashierButtonSize.LARGE -> 59.dp
    }

    val paddingValues = when(size) {
        CashierButtonSize.EXTRA_SMALL -> PaddingValues(vertical = 8.dp, horizontal = 16.dp)
        else -> PaddingValues(vertical = 16.dp, horizontal = 24.dp)
    }

    when(type) {
        CashierButtonType.PRIMARY -> {
            Button(
                modifier = Modifier
                    .then(modifier)
                    .height(height),
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CashierAppTheme.colors.blue,
                    disabledContainerColor = CashierAppTheme.colors.onBackground.copy(alpha = 0.2f)
                ),
                shape = CircleShape,
                border = BorderStroke(width = 2.dp, color = CashierAppTheme.colors.onBackground),
                contentPadding = paddingValues
            ) {
                icon?.let {
                    Icon(
                        imageVector = icon,
                        tint = Color.Unspecified,
                        contentDescription = text,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                CashierAppText(
                    text = text,
                    style = textStyle,
                    color = CashierAppTheme.colors.onBackground
                )
            }
        }
        CashierButtonType.SECONDARY -> {
            Button(
                modifier = Modifier
                    .height(height)
                    .then(modifier),
                onClick = onClick,
                enabled = isEnabled,
                colors = ButtonDefaults.buttonColors(CashierAppTheme.colors.background),
                shape = CircleShape,
                border = BorderStroke(width = 2.dp, color = CashierAppTheme.colors.onBackground),
                contentPadding = paddingValues
            ) {
                icon?.let {
                    Icon(
                        imageVector = icon,
                        tint = Color.Unspecified,
                        contentDescription = text,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                CashierAppText(
                    text = text,
                    style = textStyle,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CashierButtonPreview() {
    AppTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CashierButton(
                text = "Primary Button",
                type = CashierButtonType.PRIMARY,
                size = CashierButtonSize.MEDIUM,
                onClick = {}
            )
            CashierButton(
                text = "Secondary Button",
                type = CashierButtonType.SECONDARY,
                size = CashierButtonSize.MEDIUM,
                onClick = {}
            )
            CashierButton(
                text = "Primary Button with Icon",
                type = CashierButtonType.PRIMARY,
                size = CashierButtonSize.MEDIUM,
                icon = CashierAppTheme.icons.search,
                onClick = {}
            )
            CashierButton(
                text = "Outline Button",
                type = CashierButtonType.SECONDARY,
                size = CashierButtonSize.MEDIUM,
                icon = CashierAppTheme.icons.google,
                onClick = {}
            )
            CashierButton(
                text = "Primary Button",
                type = CashierButtonType.SECONDARY,
                size = CashierButtonSize.LARGE,
                icon = CashierAppTheme.icons.google,
                onClick = {}
            )
        }
    }
}