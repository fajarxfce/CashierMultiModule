/*
 * Copyright 2025 The Android Open Source Project
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

package com.google.samples.apps.nowinandroid.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fajarxfce.feature.onboarding.ui.OnBoardingScreen
import com.fajarxfce.feature.splash.ui.SplashScreen
import kotlinx.serialization.Serializable

@Serializable data object SplashRoute
@Serializable data object OnboardingRoute
@Serializable data object MainRoute

@Composable
fun RootNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = SplashRoute
    ){
        composable<SplashRoute> {
            SplashScreen(
                onSplashFinished = { navController.navigate(OnboardingRoute) }
            )
        }

        composable<OnboardingRoute> {
            OnBoardingScreen(
                onBoardingCompleted = {  },
            )
        }

        composable<MainRoute> {

        }
    }
}