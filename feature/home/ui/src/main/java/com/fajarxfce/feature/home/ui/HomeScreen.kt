package com.fajarxfce.feature.home.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajarxfce.core.ui.component.CashierSearchBar
import com.fajarxfce.core.ui.theme.CashierAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
internal fun HomeScreen(
    uiState: HomeContract.UiState,
    uiEffect: Flow<HomeContract.UiEffect>,
    onAction: (HomeContract.UiAction) -> Unit,
    onNavigateDetail: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            CashierSearchBar(
                query = "Test",
                onSearch = { result ->  },
                onFilterClick = {},
                onScannerClick = {},
                onQueryChange = { result ->  },
                modifier = Modifier.padding(16.dp)
            )
        },
        containerColor = CashierAppTheme.colors.background
    ) { paddingValues ->

    }

}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        uiState = HomeContract.UiState(),
        uiEffect = emptyFlow(),
        onAction = {},
        onNavigateDetail = {},
    )
}