package com.fajarxfce.core.ui.component.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajarxfce.core.ui.component.CashierText
import com.fajarxfce.core.ui.theme.AppTheme
import com.fajarxfce.core.ui.theme.CashierBlue
import com.fajarxfce.core.ui.theme.CashierBlueDisabled

@Composable
fun BigActionButtonCompose(
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isEnabled) {
            CashierBlue
        } else {
            CashierBlueDisabled
        },
        animationSpec = tween(
            durationMillis = 200,
            easing = LinearEasing
        ),
        label = ""
    )
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = backgroundColor
            ),
            modifier = Modifier
                .padding(
                    start = 32.dp,
                    end = 32.dp,
                    bottom = 16.dp,
                    top = 10.dp
                ),
            enabled = isEnabled
        ) {
            CashierText(
                text = buttonText,
                color = Color.White,
                modifier = Modifier
                    .padding(
                        top = 13.dp,
                        bottom = 13.dp
                    )
            )
        }
    }
}

@Composable
@Preview
private fun BottomButtonSegmentPreview() {
    AppTheme {
        BigActionButtonCompose(
            buttonText = "Test Click",
            onClick = {}
        )
    }
}
