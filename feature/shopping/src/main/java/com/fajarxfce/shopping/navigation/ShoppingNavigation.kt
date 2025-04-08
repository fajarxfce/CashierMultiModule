package com.fajarxfce.shopping.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fajarxfce.shopping.ui.ShoppingScreen
import kotlinx.serialization.Serializable

@Serializable data object ShoppingBaseRoute
@Serializable data object ShoppingRoute

fun NavController.navigateToAccount(navOptions: NavOptions? = null) {
    navigate(route = ShoppingRoute, navOptions)
}

fun NavGraphBuilder.shoppingSection(

) {
    navigation<ShoppingBaseRoute>(
        startDestination = ShoppingRoute,
    ) {
        composable<ShoppingRoute> {
            ShoppingScreen(
                onAddToCart = { productId, qty ->

                },
            )
        }
    }
}