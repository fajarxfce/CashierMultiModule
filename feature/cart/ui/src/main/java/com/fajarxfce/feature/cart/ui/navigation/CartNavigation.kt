package com.fajarxfce.feature.cart.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fajarxfce.feature.cart.ui.CartScreen
import kotlinx.serialization.Serializable

@Serializable data object Cart

fun NavGraphBuilder.cartScreen(){
    composable<Cart> {
        CartScreen()
    }
}