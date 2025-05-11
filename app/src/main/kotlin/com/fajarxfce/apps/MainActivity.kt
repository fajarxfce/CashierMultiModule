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
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fajarxfce.core.datastore.NiaPreferencesDataSource
import com.fajarxfce.core.ui.theme.AppTheme
import com.fajarxfce.navigation.CashierAppNavGraph
import com.fajarxfce.navigation.CashierBottomBar
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
            val navController = rememberNavController()
            val bottomBarVisibility =
                navController.currentBackStackEntryAsState().value?.destination?.route in NavigationItem.getNavigationRoutes()
            AppTheme {
                Box {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            AnimatedVisibility(bottomBarVisibility) {
                                Column {
                                    HorizontalDivider(
                                        thickness = 2.dp,
                                        color = MaterialTheme.colorScheme.onBackground,
                                    )
                                    CashierBottomBar(
                                        navController = navController,
                                    )
                                }
                            }
                        },
                    ) { innerPadding ->
                        CashierAppNavGraph(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            navController = navController,
                        )
                    }
                }
            }
        }
    }
}