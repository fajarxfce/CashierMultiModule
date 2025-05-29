package com.fajarxfce.apps.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fajarxfce.core.ui.theme.AppTheme
import com.fajarxfce.core.ui.theme.CashierBlue
import com.fajarxfce.navigation.NavigationItem
import com.fajarxfce.navigation.getRoute

val CustomDrawerBackgroundColor = Color(0xFFF0F4F8)
val CustomDrawerContentColor = Color(0xFF2C3E50)
val CustomDrawerHeaderBackgroundColor = CashierBlue
val CustomDrawerHeaderContentColor = Color.White

val CustomDrawerSelectedBackgroundColor = CashierBlue.copy(alpha = 0.15f)
val CustomDrawerSelectedContentColor = CashierBlue
val CustomDrawerIconColor = Color(0xFF7F8C8D)
val CustomDrawerSelectedIconColor = CashierBlue
val CustomDrawerDividerColor = Color.LightGray.copy(alpha = 0.6f)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppDrawerContent(
    modifier: Modifier = Modifier,
    navigationItems: List<NavigationItem>,
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit
) {
    ModalDrawerSheet(
        modifier = modifier
            .fillMaxHeight()
            .width(300.dp),
        drawerShape = RectangleShape,
        drawerContainerColor = CustomDrawerBackgroundColor,
        drawerContentColor = CustomDrawerContentColor,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                CustomDrawerHeader(
                    appName = "Cashier App",
                    appVersion = "v1.0.0"
                )
                HorizontalDivider(color = CustomDrawerDividerColor, thickness = 1.dp)
            }

            items(navigationItems) { item ->
                if (item is NavigationItem.LogoutScreen) {
                    CustomDrawerNavigationItem(
                        item = item,
                        isSelected = false,
                        onClick = { onLogout() }
                    )
                } else {
                    CustomDrawerNavigationItem(
                        item = item,
                        isSelected = currentRoute == item.route.getRoute(),
                        onClick = { onNavigate(item.route.getRoute()) }
                    )
                }

                if (item is NavigationItem.CustomersScreen) {
                    HorizontalDivider(
                        color = CustomDrawerDividerColor,
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CustomDrawerHeader(
    modifier: Modifier = Modifier,
    appName: String,
    appVersion: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(CustomDrawerBackgroundColor)
            .padding(horizontal = 20.dp, vertical = 24.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column {
            Text(
                text = appName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = CustomDrawerContentColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = appVersion,
                fontSize = 14.sp,
                color = CustomDrawerContentColor.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun CustomDrawerNavigationItem(
    item: NavigationItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) CustomDrawerSelectedBackgroundColor else Color.Transparent
    val contentColor = if (isSelected) CustomDrawerSelectedContentColor else CustomDrawerContentColor
    val iconTint = if (isSelected) CustomDrawerSelectedContentColor else CustomDrawerContentColor.copy(alpha = 0.8f)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(backgroundColor)
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            tint = iconTint,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = item.title,
            color = contentColor,
            fontSize = 15.sp, // Ukuran font kustom
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
        )
    }
}

@Preview(showBackground = true, name = "Custom Drawer Content Light")
@Composable
fun CustomAppDrawerContentPreviewLight() {
    val items = NavigationItem.getAllNavigationItem()
    AppTheme(darkTheme = false) {
        CustomAppDrawerContent(
            navigationItems = items,
            currentRoute = NavigationItem.HomeScreen.route.getRoute(),
            onNavigate = { route -> println("Navigate to: $route") },
            onLogout = { println("Logout clicked") }
        )
    }
}

@Preview(showBackground = true, name = "Custom Drawer Content Dark")
@Composable
fun CustomAppDrawerContentPreviewDark() {
    val items = NavigationItem.getAllNavigationItem()
    AppTheme(darkTheme = true) {
        CustomAppDrawerContent(
            navigationItems = items,
            currentRoute = NavigationItem.PointOfSaleScreen.route.getRoute(),
            onNavigate = { route -> println("Navigate to: $route") },
            onLogout = { println("Logout clicked") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomDrawerHeaderPreview() {
    AppTheme {
        CustomDrawerHeader(appName = "Preview App", appVersion = "v0.1")
    }
}

@Preview(showBackground = true)
@Composable
fun CustomDrawerNavigationItemSelectedPreview() {
    AppTheme {
        CustomDrawerNavigationItem(
            item = NavigationItem.HomeScreen,
            isSelected = true,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomDrawerNavigationItemPreview() {
    AppTheme {
        CustomDrawerNavigationItem(
            item = NavigationItem.PointOfSaleScreen,
            isSelected = false,
            onClick = {}
        )
    }
}