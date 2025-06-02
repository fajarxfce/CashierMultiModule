// Berkas: /core/ui/src/main/java/com/fajarxfce/core/ui/component/CashierTopAppBar.kt
package com.fajarxfce.core.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajarxfce.core.ui.component.textfield.CashierSearchTextField
import com.fajarxfce.core.ui.theme.AppTheme // Ganti dengan tema aplikasi Anda
import com.fajarxfce.core.ui.theme.CashierBackground
import com.fajarxfce.core.ui.theme.CashierBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashierTopAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    titleContent: (@Composable () -> Unit)? = null,
    showNavigationIcon: Boolean = true,
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    navigationIconContentDescription: String? = "Back",
    onNavigationIconClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        // atau topAppBarColors()
        containerColor = Color.White,
        titleContentColor = MaterialTheme.colorScheme.onPrimary,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
        actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
    ),
) {
    TopAppBar(
        title = {
            if (titleContent != null) {
                titleContent()
            } else if (title != null) {
                Text(
                    color = CashierBackground,
                    text = title
                )
            }
        },
        modifier = modifier,
        navigationIcon = {
            if (showNavigationIcon && onNavigationIconClick != null) {
                IconButton(onClick = onNavigationIconClick) {
                    Icon(
                        tint = CashierBlue,
                        imageVector = navigationIcon,
                        contentDescription = navigationIconContentDescription,
                    )
                }
            } else {
                // Tambahkan spacer jika ikon navigasi tidak ditampilkan tapi kita ingin menyeimbangkan judul
                // Ini opsional, tergantung desain. Jika titleContent adalah SearchBar fillMaxWidth, ini mungkin tidak perlu.
                if (titleContent == null && actions == {}) Spacer(Modifier.width(16.dp))
            }
        },
        actions = actions,
        colors = colors,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "TopAppBar - Title Only")
@Composable
private fun CashierTopAppBarPreviewTitle() {
    AppTheme {
        Surface {
            CashierTopAppBar(
                title = "Screen Title",
                showNavigationIcon = true,
                onNavigationIconClick = {},
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "TopAppBar - With Actions")
@Composable
private fun CashierTopAppBarPreviewWithActions() {
    AppTheme {
        Surface {
            CashierTopAppBar(
                title = "Actions Example",
                showNavigationIcon = true,
                onNavigationIconClick = {},
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "Search",
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "More options")
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "TopAppBar - Custom Title Content (Search)")
@Composable
private fun CashierTopAppBarPreviewCustomTitle() {
    AppTheme {
        Surface {
            var searchText by remember { mutableStateOf("") }
            CashierTopAppBar(
                showNavigationIcon = true,
                onNavigationIconClick = {},
                titleContent = {
                    CashierSearchTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        onImeAction = { /*TODO*/ },
                        placeholderText = "Search in TopAppBar...",
                    )
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.FilterList, contentDescription = "Filter")
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "TopAppBar - No Navigation Icon")
@Composable
private fun CashierTopAppBarPreviewNoNav() {
    AppTheme {
        Surface {
            CashierTopAppBar(
                title = "No Navigation",
                showNavigationIcon = false,
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Info, contentDescription = "Info")
                    }
                },
            )
        }
    }
}