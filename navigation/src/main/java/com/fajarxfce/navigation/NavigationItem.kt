package com.fajarxfce.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.ui.graphics.vector.ImageVector
import com.fajarxfce.feature.home.ui.navigation.Home
import com.fajarxfce.core.ui.navigation.Screen
import com.fajarxfce.feature.pos.ui.navigation.Pos
import com.fajarxfce.feature.transactionhistory.ui.navigation.TransactionHistoryRoute
import kotlinx.serialization.Serializable

@Serializable data object Customer : Screen
@Serializable data object Product : Screen
@Serializable data object Setting : Screen
@Serializable data object Support : Screen
@Serializable data object Help : Screen
@Serializable data object Logout : Screen
sealed class NavigationItem(
    var route: Screen,
    var title: String,
    val icon : ImageVector
) {
    data object HomeScreen : NavigationItem(
        route = Home,
        title = "Home",
        icon = Icons.Filled.Home
    )

    data object PointOfSaleScreen : NavigationItem(
        route = Pos,
        title = "Point Of Sale",
        icon = Icons.Filled.PointOfSale
    )
    data object CustomersScreen : NavigationItem(
        route = Customer,
        title = "Point Of Sale",
        icon = Icons.Filled.People
    )

    data object ProductsScreen : NavigationItem(
        route = Product,
        title = "Product Management",
        icon = Icons.Filled.Inventory2
    )

    data object TransactionsHistoryScreen : NavigationItem(
        route = TransactionHistoryRoute,
        title = "Transactions History",
        icon = Icons.Filled.ListAlt
    )

    data object SettingsScreen : NavigationItem(
        route = Setting,
        title = "Settings",
        icon = Icons.Filled.Settings
    )

    data object SupportScreen : NavigationItem(
        route = Support,
        title = "Support",
        icon = Icons.Filled.SupportAgent
    )

    data object HelpScreen : NavigationItem(
        route = Help,
        title = "Help",
        icon = Icons.AutoMirrored.Filled.ListAlt
    )

    data object LogoutScreen : NavigationItem(
        route = Logout,
        title = "Point Of Sale",
        icon = Icons.Filled.Logout
    )


    companion object {
        fun getNavigationRoutes() = listOf(
            HomeScreen.route.getRoute(),
        )
        fun getAllNavigationItem() = listOf(
            HomeScreen,
            PointOfSaleScreen,
            ProductsScreen,
            TransactionsHistoryScreen,
            CustomersScreen,
            SettingsScreen,
            SupportScreen,
            HelpScreen,
            LogoutScreen,
        )

    }

}