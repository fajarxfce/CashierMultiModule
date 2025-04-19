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
    itemCount: Int,
    totalPrice: Double,
    onViewCartClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$itemCount items: $${String.format("%.2f", totalPrice)}",
            color = MaterialTheme.colorScheme.primary,
        )

        Button(onClick = onViewCartClick) {
            Text("View Cart")
        }
    }
}

@Preview
@Composable
private fun CartSummaryBarPreview() {
    CartSummaryBar(
        itemCount = 3,
        totalPrice = 29.99,
        onViewCartClick = {}
    )
}