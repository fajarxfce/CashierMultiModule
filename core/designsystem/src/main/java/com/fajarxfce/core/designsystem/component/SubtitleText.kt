package com.fajarxfce.core.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SubtitleText(subtitle: String) {  // Or PriceText if you want a more specific one
    Text(
        text = subtitle,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
    )
}

@Preview
@Composable
private fun SubtitleTextPreview() {
    SubtitleText("Subtitle Text")
}

