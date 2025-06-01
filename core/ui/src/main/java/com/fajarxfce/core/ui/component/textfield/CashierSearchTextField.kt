// Berkas: /core/ui/src/main/java/com/fajarxfce/core/ui/component/textfield/CashierSearchTextField.kt

package com.fajarxfce.core.ui.component.textfield

// ... import lainnya ...
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.animation.core.copy
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList // Icon untuk filter
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajarxfce.core.ui.R
import com.fajarxfce.core.ui.component.CashierIcon
import com.fajarxfce.core.ui.component.CashierText
import com.fajarxfce.core.ui.component.button.CashierIconButton
import com.fajarxfce.core.ui.theme.AppTheme
import com.fajarxfce.core.ui.theme.CashierGreySuit

// ...

@Composable
fun CashierSearchTextField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onImeAction: () -> Unit = {},
    showFilterIcon: Boolean = false,
    onFilterClick: (() -> Unit)? = null,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        modifier = modifier
            .fillMaxWidth(),
        value = text,
        onValueChange = onTextChange,
        shape = RoundedCornerShape(20.dp),
        leadingIcon = {
            CashierIcon(
                imageVector = Icons.Filled.Search,
                tint = CashierGreySuit,
            )
        },
        trailingIcon = {
            if (showFilterIcon && onFilterClick != null) {
                IconButton(onClick = onFilterClick) {
                    CashierIconButton(
                        imageVector = Icons.Filled.FilterList,
                        onClick = {},
                    )
                }
            }
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            ),
        placeholder = {
            CashierText(
                text = stringResource(
                    id = R.string.core_ui_action_search,
                ),
                color = CashierGreySuit,
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onImeAction()
                keyboardController?.hide()
            },
        ),
    )
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
private fun CashierSearchTextFieldPreviewWithFilter() {
    AppTheme {
        CashierSearchTextField(
            text = "Search term",
            onTextChange = {},
            onImeAction = {},
            showFilterIcon = true,
            onFilterClick = {},
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
private fun CashierSearchTextFieldPreviewNoFilter() {
    AppTheme {
        CashierSearchTextField(
            text = "",
            onTextChange = {},
            onImeAction = {},
            showFilterIcon = false, // Atau biarkan default
        )
    }
}