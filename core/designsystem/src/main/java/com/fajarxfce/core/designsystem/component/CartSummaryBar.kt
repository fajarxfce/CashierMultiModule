package com.fajarxfce.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CartSummaryBar(
    totalAmount: Double,
    onCheckoutClick: () -> Unit,
    modifier: Modifier = Modifier,
    itemCount: Int = 0
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Total: $${String.format("%.2f", totalAmount)}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Button(onClick = onCheckoutClick) {
            Text("Checkout")
        }
    }
}

@Preview
@Composable
private fun CartSummaryBarPreview() {
    CartSummaryBar(
        totalAmount = 129.99,
        onCheckoutClick = {}
    )
}