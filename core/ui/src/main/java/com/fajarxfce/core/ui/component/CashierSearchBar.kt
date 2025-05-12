package com.fajarxfce.core.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajarxfce.core.ui.extension.boldBorder
import com.fajarxfce.core.ui.theme.CashierAppTheme

/**
 * A modern search bar component designed for cashier applications.
 *
 * @param query Current search query text
 * @param onQueryChange Callback when search query changes
 * @param onSearch Callback when search action is performed
 * @param modifier Modifier to be applied to the search bar
 * @param placeholder Text to show when search field is empty
 * @param showScannerButton Whether to show barcode scanner button
 * @param onScannerClick Callback when scanner button is clicked
 * @param showFilterButton Whether to show product filter button
 * @param onFilterClick Callback when filter button is clicked
 */
@Composable
fun CashierSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search products or scan barcode...",
    showScannerButton: Boolean = true,
    onScannerClick: () -> Unit = {},
    showFilterButton: Boolean = true,
    onFilterClick: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(28.dp))
            .boldBorder(28, CashierAppTheme.colors.onBackground),
        color = CashierAppTheme.colors.background,
        tonalElevation = 2.dp,
        shadowElevation = 4.dp,
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            placeholder = {
                CashierAppText(
                    text = placeholder,
                    color = CashierAppTheme.colors.onBackground.copy(alpha = 0.7f),
                    style = CashierAppTheme.typography.paragraph2,
                    maxLines = 1,
                )
            },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search icon",
                    tint = CashierAppTheme.colors.onBackground
                )
            },
            trailingIcon = {
                Row {
                    AnimatedVisibility(visible = query.isNotEmpty()) {
                        IconButton(onClick = { onQueryChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear search",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    if (showFilterButton) {
                        IconButton(onClick = onFilterClick) {
                            Icon(
                                imageVector = Icons.Default.FilterList,
                                contentDescription = "Filter products",
                                tint = CashierAppTheme.colors.onBackground
                            )
                        }
                    }

                    if (showScannerButton) {
                        IconButton(onClick = onScannerClick) {
                            Icon(
                                imageVector = Icons.Default.QrCodeScanner,
                                contentDescription = "Scan barcode",
                                tint = CashierAppTheme.colors.onBackground
                            )
                        }
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(query)
                    focusManager.clearFocus()
                }
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
    }
}

/**
 * Preview for CashierSearchBar
 */
@Preview(showBackground = true)
@Composable
private fun CashierSearchBarPreview() {
    var searchQuery by remember { mutableStateOf("") }
    CashierSearchBar(
        query = searchQuery,
        onQueryChange = { searchQuery = it },
        onSearch = { /* Handle search */ },
    )
}