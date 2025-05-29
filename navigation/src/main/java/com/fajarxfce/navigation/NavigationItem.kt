package com.fajarxfce.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.ui.graphics.vector.ImageVector
import com.fajarxfce.feature.home.ui.navigation.Home
import com.fajarxfce.core.ui.navigation.Screen
import com.fajarxfce.feature.pos.ui.navigation.Pos

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
        route = Home,
        title = "Point Of Sale",
        icon = Icons.Filled.Home
    )

    data object LogoutScreen : NavigationItem(
        route = Home,
        title = "Point Of Sale",
        icon = Icons.Filled.Home
    )
    data object CustomersScreen : NavigationItem(
        route = Home,
        title = "Point Of Sale",
        icon = Icons.Filled.Home
    )

    companion object {
        fun getNavigationRoutes() = listOf(
            HomeScreen.route.getRoute(),
        )
        fun getAllNavigationItem() = listOf(
            HomeScreen,
            PointOfSaleScreen
        )

    }

}