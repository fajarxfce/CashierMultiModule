package com.fajarxfce.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val hasBadge: Boolean = false,
    val badgeCount: Int? = null,
)

@Composable
fun CustomBottomNavigationBar() {
    val items = listOf(
        BottomNavItem(title = "Lowongan", icon = Icons.Filled.Home),
        BottomNavItem(title = "ExpertClass", icon = Icons.Filled.Bookmark),
        BottomNavItem(
            title = "Chat",
            icon = Icons.Filled.ChatBubble,
            hasBadge = true,
            badgeCount = 1,
        ),
        BottomNavItem(title = "Saya", icon = Icons.Filled.Person),
    )
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(vertical = 4.dp),
        containerColor = Color.White, // Assuming white background like in the image
        contentColor = MaterialTheme.colorScheme.background, // You can customize the active color
        tonalElevation = 8.dp,
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.hasBadge && item.badgeCount != null && item.badgeCount > 0) {
                                Badge {
                                    Text(item.badgeCount.toString())
                                }
                            }
                        },
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title)
                    }
                },
                label = {
                    Text(text = item.title, fontSize = 10.sp)
                },
                selected = false, // You'll likely want to manage selection state
                onClick = {
                    // Handle navigation here
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomBottomNavigationBar() {
    CustomBottomNavigationBar()
}