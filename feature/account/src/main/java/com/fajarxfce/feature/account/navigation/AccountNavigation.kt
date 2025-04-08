package com.fajarxfce.feature.account.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fajarxfce.feature.account.ui.AccountScreen
import kotlinx.serialization.Serializable

@Serializable data object AccountBaseRoute
@Serializable data object AccountRoute

fun NavController.navigateToAccount(navOptions: NavOptions? = null) {
    navigate(route = AccountRoute, navOptions)
}

fun NavGraphBuilder.accountSection(

) {
    navigation<AccountBaseRoute>(
        startDestination = AccountRoute
    ) {
        composable<AccountRoute> {
            AccountScreen(
                onNavigateToStoreManagement = {},
                onNavigateToTransactionHistory = {},
                onNavigateToSettings = {},
                onSignOut = {}
            )
        }
    }
}