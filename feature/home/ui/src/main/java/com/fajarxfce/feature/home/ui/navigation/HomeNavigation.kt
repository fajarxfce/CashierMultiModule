package com.fajarxfce.feature.home.ui.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fajarxfce.feature.home.ui.HomeViewModel
import com.fajarxfce.core.ui.navigation.Screen
import com.fajarxfce.feature.home.ui.HomeScreen
import kotlinx.serialization.Serializable

@Serializable data object Home : Screen

fun NavGraphBuilder.homeScreen(
    onNavigateSearch: () -> Unit,
    onNavigateDetail: (Int) -> Unit,
    onNavigateDetailWithArgs: () -> Unit,
    onNavigateToPos: () -> Unit,
    onOpenDrawer: () -> Unit,
) {
    composable<Home> {
        val viewModel: HomeViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val uiEffect = viewModel.uiEffect
        HomeScreen(
            uiState = uiState,
            uiEffect = uiEffect,
            onAction = viewModel::onAction,
            onNavigateDetail = onNavigateDetail,
            onNavigateToPos = onNavigateToPos,
            onOpenDrawer = onOpenDrawer
        )
    }
}