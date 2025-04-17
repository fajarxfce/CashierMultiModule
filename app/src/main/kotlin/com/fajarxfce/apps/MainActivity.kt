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
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.fajarxfce.apps.navigation.RootNavHost
import com.fajarxfce.core.AuthEvent
import com.fajarxfce.core.AuthEventBus
import com.fajarxfce.core.datastore.NiaPreferencesDataSource
import com.fajarxfce.core.designsystem.theme.AppTheme
import com.fajarxfce.feature.auth.navigation.AuthBaseRoute
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var niaPreferencesDataSource: NiaPreferencesDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            AppTheme(navController = navController) {
                RootNavHost(
                    navController = navController,
                )
            }

            LaunchedEffect(navController) {
                AuthEventBus.events.collectLatest { event ->
                    when (event) {
                        is AuthEvent.Logout -> {
                            Timber.d("Navigating to login screen")
                            navController.navigate(AuthBaseRoute) {
                                popUpTo(navController.currentBackStackEntry?.destination?.route.toString()) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    }
                }
            }
        }
    }
}