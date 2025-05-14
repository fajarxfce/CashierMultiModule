package com.fajarxfce.feature.pos.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fajarxfce.feature.pos.ui.PosScreen
import kotlinx.serialization.Serializable

@Serializable data object Pos

fun NavGraphBuilder.posScreen(
    onNavigateBack: () -> Unit,
    onNavigateDetail: (Int) -> Unit,
){
    composable<Pos> {
        PosScreen(
            onNavigateBack = onNavigateBack,
            onNavigateDetail = onNavigateDetail,
        )
    }
}