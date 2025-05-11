package com.fajarxfce.navigation

import com.fajarxfce.feature.home.ui.navigation.Home
import com.fajarxfce.core.ui.navigation.Screen

sealed class NavigationItem(
    var route: Screen,
    var title: Int,
    val selectedIcon: Int,
    val unselectedIcon: Int,
) {
    data object HomeScreen : NavigationItem(
        route = Home,
        title = R.string.navigation_home,
        selectedIcon = R.drawable.navigation_ic_home_selected,
        unselectedIcon = R.drawable.navigation_ic_home_unselected
    )

    data object FavoriteScreen : NavigationItem(
        route = Home,
        title = R.string.navigation_favorites,
        selectedIcon = R.drawable.navigation_ic_star_selected,
        unselectedIcon = R.drawable.navigation_ic_star_unselected
    )

    data object LeaderBoardScreen : NavigationItem(
        route = Home,
        title = R.string.navigation_leaderboard,
        selectedIcon = R.drawable.navigation_ic_leaderboard_selected,
        unselectedIcon = R.drawable.navigation_ic_leaderboard_unselected
    )

    data object ProfileScreen : NavigationItem(
        route = Home,
        title = R.string.navigation_profile,
        selectedIcon = R.drawable.navigation_ic_profile_selected,
        unselectedIcon = R.drawable.navigation_ic_profile_unselected
    )

    companion object {
        fun getNavigationRoutes() = listOf(
            HomeScreen.route.javaClass.canonicalName.orEmpty(),
            FavoriteScreen.route.javaClass.canonicalName.orEmpty(),
            LeaderBoardScreen.route.javaClass.canonicalName.orEmpty(),
            ProfileScreen.route.javaClass.canonicalName.orEmpty(),
        )
        fun getNavigationItems() = listOf(
            HomeScreen,
            FavoriteScreen,
            LeaderBoardScreen,
            ProfileScreen
        )
    }

}