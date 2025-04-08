package com.fajarxfce.feature.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun MainNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "",
    ) {

    }
}