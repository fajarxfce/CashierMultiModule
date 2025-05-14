package com.fajarxfce.apps

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fajarxfce.navigation.NavigationItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AppState(
    val navController: NavHostController,
    val drawerState: DrawerState,
    val coroutineScope: CoroutineScope,
) {

    val shouldShowDrawer: Boolean
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route in NavigationItem.getNavigationRoutes()

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    fun openDrawer() {
        coroutineScope.launch {
            drawerState.open()
        }
    }

    fun closeDrawer() {
        coroutineScope.launch {
            drawerState.close()
        }
    }
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = AppState(
    navController = navController,
    drawerState = drawerState,
    coroutineScope = coroutineScope
)