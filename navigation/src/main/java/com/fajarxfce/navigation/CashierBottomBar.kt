package com.fajarxfce.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fajarxfce.core.ui.component.CashierAppText
import com.fajarxfce.core.ui.extension.boldBorder
import com.fajarxfce.core.ui.extension.conditional
import com.fajarxfce.core.ui.extension.noRippleClickable
import com.fajarxfce.core.ui.theme.CashierAppTheme


@SuppressLint("RestrictedApi")
@Composable
fun CashierBottomBar(
    navController: NavController,
) {
    val tabList = NavigationItem.getNavigationItems()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar(
        modifier = Modifier.padding(horizontal = 16.dp),
        containerColor = CashierAppTheme.colors.background,
    ) {
        tabList.forEach { navItem ->
            val isSelected = currentRoute == navItem.route.getRoute()
            key(navItem.route.getRoute()) {
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .weight(if (isSelected) 1f else 0.5f)
                        .background(
                            color = if (isSelected) CashierAppTheme.colors.blue else CashierAppTheme.colors.background,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .conditional(isSelected) { boldBorder() }
                        .noRippleClickable {
                            navController.navigate(navItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        .padding(
                            horizontal = if (isSelected) 20.dp else 0.dp,
                            vertical = 12.dp,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier.size(if (isSelected) 16.dp else 24.dp),
                        imageVector = ImageVector.vectorResource(
                            if (isSelected) navItem.selectedIcon else navItem.unselectedIcon
                        ),
                        tint = if (isSelected) CashierAppTheme.colors.background else CashierAppTheme.colors.onBackground,
                        contentDescription = null,
                    )
                    AnimatedVisibility(isSelected) {
//                        CashierAppText(
//                            modifier = Modifier.padding(start = 8.dp),
//                            text = stringResource(navItem.title),
//                            style = CashierAppTheme.typography.paragraph2,
//                            color = CashierAppTheme.colors.background,
//                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizAppBottomBarPreview() {
    CashierBottomBar(
        navController = rememberNavController(),
    )
}