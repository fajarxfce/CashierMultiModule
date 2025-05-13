package com.fajarxfce.core.ui.component.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fajarxfce.core.ui.component.CashierIcon
import com.fajarxfce.core.ui.theme.AppTheme
import com.fajarxfce.core.ui.theme.CashierBlue

@Composable
fun StandardFilledButton(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
    enabled: Boolean = true,
    imageVector: ImageVector,
    backgroundColor: Color = CashierBlue,
    text: String? = null,
    iconTint: Color? = null
) {
    val borderStroke = if (backgroundColor == CashierBlue) {
        null
    } else {
        BorderStroke(
            width = 1.dp,
            color = CashierBlue
        )
    }
    Button(
        onClick = onButtonClick,
        modifier = modifier,
        border = borderStroke,
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        enabled = enabled
    ) {
        if (text != null) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 17.sp,
                modifier = Modifier
                    .padding(
                        end = 8.dp
                    )
            )
        }
        if (iconTint != null) {
            CashierIcon(
                imageVector = imageVector,
                tint = iconTint
            )
        } else {
            CashierIcon(imageVector = imageVector)
        }
    }
}

@Composable
fun StandardFilledButton(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
    enabled: Boolean = true,
    @DrawableRes iconDrawable: Int,
    backgroundColor: Color = CashierBlue,
    text: String? = null,
    iconTint: Color? = null
) {
    val borderStroke = if (backgroundColor == CashierBlue) {
        null
    } else {
        BorderStroke(
            width = 1.dp,
            color = CashierBlue
        )
    }
    Button(
        onClick = onButtonClick,
        modifier = modifier,
        border = borderStroke,
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        enabled = enabled
    ) {
        if (text != null) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 17.sp,
                modifier = Modifier
                    .padding(
                        end = 8.dp
                    )
            )
        }
        if (iconTint != null) {
            CashierIcon(
                painter = painterResource(id = iconDrawable),
                tint = iconTint
            )
        } else {
            CashierIcon(
                painter = painterResource(id = iconDrawable)
            )
        }
    }
}

@Composable
@Preview
private fun StandardFilledButtonPreview() {
    AppTheme {
        StandardFilledButton(
            modifier = Modifier
                .fillMaxWidth(),
            onButtonClick = {},
            imageVector = Icons.Filled.Search,
            text = "4"
        )
    }
}