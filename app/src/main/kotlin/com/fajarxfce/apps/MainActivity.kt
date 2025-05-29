package com.fajarxfce.apps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fajarxfce.apps.component.CustomAppDrawerContent
import com.fajarxfce.core.datastore.NiaPreferencesDataSource
import com.fajarxfce.core.ui.theme.AppTheme
import com.fajarxfce.navigation.CashierAppNavGraph
import com.fajarxfce.navigation.NavigationItem
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var niaPreferencesDataSource: NiaPreferencesDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                val appState = rememberAppState()
                MainAppScreen(appState = appState)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(appState: AppState) {
    val scope = rememberCoroutineScope()
    val navigationItems = remember { NavigationItem.getAllNavigationItem() }

    ModalNavigationDrawer(
        drawerState = appState.drawerState,
        drawerContent = {
            CustomAppDrawerContent(
                navigationItems = navigationItems,
                currentRoute = appState.currentDestination?.route,
                onNavigate = { route ->
                    appState.navController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                    appState.closeDrawer()
                },
                onLogout = {
                    println("Logout action performed in MainActivity")
                    appState.closeDrawer()
                },
            )
        },
        gesturesEnabled = appState.shouldShowDrawer,
    ) {
        Scaffold(
        ) { innerPadding ->
            CashierAppNavGraph(
                navController = appState.navController,
                modifier = Modifier.fillMaxSize(),
                onOpenDrawer = appState::openDrawer,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainAppScreenPreview() {
    val appState = rememberAppState()
    MainAppScreen(appState)
}