package com.fajarxfce.navigation

import androidx.navigation.NavHostController
import com.fajarxfce.core.ui.navigation.Screen

fun NavHostController.navigateWithPopUpTo(screen: Any, popUp: Any) {
    navigate(screen) {
        popUpTo(popUp) { inclusive = true }
    }
}

fun Screen.getRoute(): String {
    return this::class.java.canonicalName.orEmpty()
}