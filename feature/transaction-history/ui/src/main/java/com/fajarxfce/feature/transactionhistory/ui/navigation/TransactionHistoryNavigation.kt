package com.fajarxfce.feature.transactionhistory.ui.navigation

import androidx.annotation.Keep
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fajarxfce.core.ui.navigation.Screen
import com.fajarxfce.feature.transactionhistory.domain.model.TransactionHistory
import com.fajarxfce.feature.transactionhistory.ui.TransactionHistoryScreen
import com.fajarxfce.feature.transactionhistory.ui.TransactionHistoryViewModel
import kotlinx.serialization.Serializable
@Keep
@Serializable data object TransactionHistoryRoute : Screen

fun NavGraphBuilder.transactionHistoryScreen(
    onNavigateBack: () -> Unit,
    onTransactionClick: (Int) -> Unit,
) {
    composable <TransactionHistoryRoute> {

        val viewModel = hiltViewModel<TransactionHistoryViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val uiEffect = viewModel.uiEffect
        val onAction = viewModel::onAction
        TransactionHistoryScreen(
            uiState = uiState,
            uiEffect = uiEffect,
            uiAction = onAction,
            onNavigateBack = onNavigateBack,
            onTransactionClick = onTransactionClick
        )
    }
}