package com.fajarxfce.feature.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fajarxfce.feature.main.ui.MainScreen
import kotlinx.serialization.Serializable

@Serializable data object MainRoute

fun NavController.navigateToMain() {
    navigate(MainRoute)
}

fun NavGraphBuilder.mainSection(

){
    composable<MainRoute> {
        MainScreen()
    }
}