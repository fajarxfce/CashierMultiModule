package com.fajarxfce.feature.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajarxfce.core.ui.component.CashierSearchBar
import com.fajarxfce.core.ui.theme.CashierAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    uiState: HomeContract.UiState,
    uiEffect: Flow<HomeContract.UiEffect>,
    onAction: (HomeContract.UiAction) -> Unit,
    onNavigateDetail: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                actions = {
                    CashierSearchBar(
                        modifier = Modifier.padding(16.dp),
                        placeholder = "Search",
                        query = "aaa",
                        onQueryChange = {},
                        onSearch = {},
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CashierAppTheme.colors.background,
                    titleContentColor = CashierAppTheme.colors.onBackground,
                    actionIconContentColor = CashierAppTheme.colors.onBackground,
                ),
            )
        },
        containerColor = CashierAppTheme.colors.background,
        contentWindowInsets = WindowInsets.navigationBars,
        ) { paddingValues ->
        HomeContent(
            modifier = Modifier
                .padding(paddingValues)
        )
    }
}

@Composable
private fun HomeContent(modifier: Modifier = Modifier) {

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