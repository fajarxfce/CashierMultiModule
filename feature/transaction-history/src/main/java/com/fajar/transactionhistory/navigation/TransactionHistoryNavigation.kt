package com.fajar.transactionhistory.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fajar.transactionhistory.ui.TransactionHistoryScreen
import kotlinx.serialization.Serializable

@Serializable data object TransactionHistoryBaseRoute
@Serializable data object TransactionHistoryRoute

fun NavController.navigateToTransactionHistory() {
    navigate(TransactionHistoryBaseRoute) {
        launchSingleTop = true
        restoreState = true
    }
}

fun NavGraphBuilder.transactionHistorySection(
    onNavigateToTransactionDetail: (String) -> Unit,
    onNavigateToTransactionHistory: () -> Unit
) {
    navigation<TransactionHistoryBaseRoute>(
        startDestination = TransactionHistoryRoute
    ) {
         composable<TransactionHistoryRoute> {
             TransactionHistoryScreen()
         }
    }
}