package com.fajarxfce.feature.home.ui.navigation

import androidx.annotation.Keep
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fajarxfce.feature.home.ui.HomeViewModel
import com.fajarxfce.core.ui.navigation.Screen
import com.fajarxfce.feature.home.ui.HomeScreen
import kotlinx.serialization.Serializable
@Keep
@Serializable data object Home : Screen

fun NavGraphBuilder.homeScreen(
    onNavigateSearch: () -> Unit,
    onNavigateDetail: (Int) -> Unit,
    onNavigateDetailWithArgs: () -> Unit,
    onNavigateToPos: () -> Unit,
    onOpenDrawer: () -> Unit,
    onNavigateToReport: () -> Unit
) {
    composable<Home>(
        enterTransition = {
            return@composable fadeIn(tween(1000))
        }, exitTransition = {
            return@composable fadeOut(tween(700))
        }, popEnterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End, tween(700)
            )
        }
    ) {
        val viewModel: HomeViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val uiEffect = viewModel.uiEffect
        HomeScreen(
            uiState = uiState,
            uiEffect = uiEffect,
            onAction = viewModel::onAction,
            onNavigateDetail = onNavigateDetail,
            onNavigateToPos = onNavigateToPos,
            onOpenDrawer = onOpenDrawer,
            onNavigateToReport = onNavigateToReport
        )
    }
}