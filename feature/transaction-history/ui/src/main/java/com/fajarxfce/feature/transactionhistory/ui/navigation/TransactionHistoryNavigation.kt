package com.fajarxfce.feature.transactionhistory.ui.navigation

import androidx.annotation.Keep
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fajarxfce.core.ui.navigation.Screen
import kotlinx.serialization.Serializable
@Keep
@Serializable data object TransactionHistory : Screen

fun NavGraphBuilder.transactionHistoryScreen(

) {
    composable <TransactionHistory> {

    }
}