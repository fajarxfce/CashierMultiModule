package com.fajarxfce.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DataCard(
    title: String,
    subtitle: String,
    imageUrl: String?,
    quantity: Int,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column {
            DisplayImage(imageUrl = imageUrl)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(12.dp),
            ) {
                TitleText(title = title)
                Spacer(modifier = Modifier.height(4.dp))
                SubtitleText(subtitle = subtitle)
                Spacer(modifier = Modifier.height(8.dp))
                QuantitySelector(
                    quantity = quantity,
                    onIncrement = onIncreaseQuantity,
                    onDecrement = onDecreaseQuantity,
                    enabled = quantity > 0,
                )
            }
        }
    }
}