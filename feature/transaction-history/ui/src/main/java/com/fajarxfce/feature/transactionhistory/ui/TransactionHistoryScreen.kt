package com.fajarxfce.feature.transactionhistory.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fajarxfce.core.ui.theme.AppTheme

@Composable
internal fun TransactionHistoryScreen(modifier: Modifier = Modifier) {
    Scaffold(

    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Transaction History Screen"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionHistoryScreenPreview() {
    AppTheme {
        TransactionHistoryScreen()
    }
}