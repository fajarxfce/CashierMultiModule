package com.fajarxfce.feature.home.ui.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fajarxfce.feature.home.ui.HomeViewModel
import com.fajarxfce.core.ui.navigation.Screen
import kotlinx.serialization.Serializable

@Serializable data object Home : Screen

fun NavGraphBuilder.homeScreen(
    onNavigateSearch: () -> Unit,
    onNavigateDetail: () -> Unit,
    onNavigateDetailWithArgs: () -> Unit,
) {
    composable<Home> {
        val viewModel: HomeViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    }
}