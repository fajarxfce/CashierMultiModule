package com.fajarxfce.feature.home.ui

import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

internal fun HomeScreen(
    uiState: HomeContract.UiState,
    onAction: (HomeContract.UiAction) -> Unit,
    onNavigateDetail: (Int) -> Unit,
) {

}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        uiState = HomeContract.UiState(),
        onAction = {},
        onNavigateDetail = {},
    )
}