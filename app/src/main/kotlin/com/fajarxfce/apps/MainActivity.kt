/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fajarxfce.apps

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fajarxfce.core.datastore.NiaPreferencesDataSource
import com.fajarxfce.core.ui.component.CashierText
import com.fajarxfce.core.ui.theme.AppTheme
import com.fajarxfce.core.ui.theme.CashierBackground
import com.fajarxfce.core.ui.theme.CashierBlue
import com.fajarxfce.core.ui.theme.CashierGray
import com.fajarxfce.core.ui.theme.CashierGreySuit
import com.fajarxfce.navigation.CashierAppNavGraph
import com.fajarxfce.navigation.NavigationItem
import com.fajarxfce.navigation.getRoute
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var niaPreferencesDataSource: NiaPreferencesDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            Log.d("MainActivity", "onCreate: ${niaPreferencesDataSource.userData.collect { 
                Log.d("MainActivity", "onCreate: $it")

            } ?: "null"}")
        }
        setContent {

            val appState = rememberAppState()
            val scope = rememberCoroutineScope()
            val items = listOf(
                MenuItem(
                    title = "Home",
                    icon = com.fajarxfce.navigation.R.drawable.navigation_ic_home_selected,
                    route = NavigationItem.HomeScreen.route.getRoute()
                ),
                MenuItem(
                    title = "Settings",
                    icon = com.fajarxfce.core.ui.R.drawable.core_ui_ic_exit,
                    route = NavigationItem.HomeScreen.route.getRoute()
                )
            )
            val selectedItem = remember { mutableStateOf(items[0]) }

            AppTheme {
                ModalNavigationDrawer(
                    drawerState = appState.drawerState,
                    drawerContent = {
                        ModalDrawerSheet(
                            drawerContainerColor = CashierBackground,
                            drawerContentColor = Color.White,
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = CashierBlue,
                                        shape = androidx.compose.foundation.shape.RoundedCornerShape(
                                            0.dp,
                                            0.dp,
                                            16.dp,
                                            16.dp
                                        )
                                    )
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .padding(16.dp),
                            ) {
                                Column(
                                    modifier = Modifier.align(androidx.compose.ui.Alignment.BottomStart)
                                ) {
                                    Text(
                                        text = "Cashier App",
                                        style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "Manage your business",
                                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                        color = Color.White.copy(alpha = 0.7f)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            NavigationItem.getNavigationItems().forEach { item ->
                                NavigationDrawerItem(
                                    icon = {
                                        Icon(
                                            imageVector = when(item) {
                                                NavigationItem.HomeScreen.route -> Icons.Filled.Home
                                                else -> Icons.Filled.List
                                            },
                                            contentDescription = null,
                                            tint = CashierBlue
                                        )
                                    },
                                    label = {
                                        CashierText(
                                            text = item.route.getRoute(),
                                        )
                                    },
                                    selected = appState.currentDestination?.route == item.route.toString(),
                                    onClick = {
                                        scope.launch {
                                            appState.drawerState.close()
                                        }

                                    },
                                    modifier = Modifier.padding(horizontal = 12.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = CashierGreySuit.copy(alpha = 0.3f)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            NavigationDrawerItem(
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.Settings,
                                        contentDescription = null,
                                        tint = CashierGray
                                    )
                                },
                                label = {
                                    Text(text = "Settings")
                                },
                                selected = false,
                                onClick = { appState.closeDrawer() },
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        }
                    },
                    gesturesEnabled = appState.shouldShowDrawer,
                ) {
                    Scaffold(
                    ) { contentPadding ->
                        CashierAppNavGraph(
                            navController = appState.navController,
                            modifier = Modifier
                                .fillMaxSize(),
                            onOpenDrawer = appState::openDrawer,
                            onCloseDrawer = appState::closeDrawer,
                        )
                    }
                }
            }
        }
    }
}

data class MenuItem(
    val title: String,
    val icon: Int,
    val route: String,
)