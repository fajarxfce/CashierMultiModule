package com.fajarxfce.feature.main.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Transition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.fajar.transactionhistory.navigation.transactionHistorySection
import com.fajarxfce.feature.account.navigation.accountSection
import com.fajarxfce.shopping.navigation.ShoppingBaseRoute
import com.fajarxfce.shopping.navigation.ShoppingRoute
import com.fajarxfce.shopping.navigation.shoppingSection
import kotlinx.serialization.Serializable

@Serializable data object AccountRoute

@Composable
fun MainNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = ShoppingBaseRoute,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        accountSection(
            onNavigateToStoreManagement = {},
            onNavigateToTransactionHistory = {},
            onNavigateToSettings = {},
            onSignOut = {}
        )
        shoppingSection()
        transactionHistorySection(
            onNavigateToTransactionDetail = {},
            onNavigateToTransactionHistory = {},
        )
    }
}