package com.fajarxfce.core.ui.component.button

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajarxfce.core.ui.theme.AppTheme
import com.fajarxfce.core.ui.theme.CashierBlue
import com.fajarxfce.core.ui.theme.CashierGray

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CounterButton(
    modifier: Modifier = Modifier,
    count: Int = 0,
    onIncrement: () -> Unit = {},
    onDecrement: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .width(140.dp)
            .height(70.dp),
        colors = CardDefaults.cardColors(
            containerColor = CashierGray.copy(alpha = 0.15f),
            contentColor = CashierGray
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .padding(6.dp)
        ) {
            // Minus Button
            IconButton(
                onClick = onDecrement,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = CashierBlue
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Remove",
                    tint = Color.White
                )
            }

            // Counter Text
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            // Plus Button
            IconButton(
                onClick = onIncrement,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = CashierBlue
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CounterButtonPreview() {
    AppTheme {
        CounterButton(
            modifier = Modifier
                .height(70.dp)
                .width(140.dp)
        )
    }
}