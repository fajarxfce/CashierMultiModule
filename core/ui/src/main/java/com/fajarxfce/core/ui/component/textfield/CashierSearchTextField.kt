// Berkas: /core/ui/src/main/java/com/fajarxfce/core/ui/component/textfield/CashierSearchTextField.kt
package com.fajarxfce.core.ui.component.textfield

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn // Gunakan heightIn untuk tinggi minimum
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajarxfce.core.ui.R // Pastikan R diimport dengan benar
import com.fajarxfce.core.ui.component.CashierIcon
import com.fajarxfce.core.ui.theme.AppTheme // Ganti dengan tema Anda jika berbeda
import com.fajarxfce.core.ui.theme.CashierBlue
import com.fajarxfce.core.ui.theme.CashierGray
import com.fajarxfce.core.ui.theme.CashierGreySuit

@Composable
fun CashierSearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onImeAction: () -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = stringResource(id = R.string.core_ui_action_search),
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        imeAction = ImeAction.Search,
    ),
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.bodyMedium,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 45.dp)
            .border(
                width = 1.dp,
                color = if (enabled) CashierBlue else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), // Warna border saat disabled
                shape = RoundedCornerShape(30.dp),
            ),
        enabled = enabled,
        shape = RoundedCornerShape(30.dp),
        leadingIcon = {
            CashierIcon(
                imageVector = Icons.Filled.Search,
                tint = if (enabled) CashierGreySuit else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = DefaultAlpha,
                ),
            )
        },
        placeholder = {
            Text(
                text = placeholderText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = CashierGray,
            unfocusedTextColor = CashierGray,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = CashierBlue,
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onSearch = {
                onImeAction()
                keyboardController?.hide()
            },
        ),
        singleLine = true,
    )
}

@Preview(showBackground = true, name = "CashierSearchTextField - Empty")
@Composable
private fun CashierSearchTextFieldPreviewEmpty() {
    AppTheme {
        Surface {
            CashierSearchTextField(
                value = "",
                onValueChange = {},
                onImeAction = {},
            )
        }
    }
}

@Preview(showBackground = true, name = "CashierSearchTextField - Filled")
@Composable
private fun CashierSearchTextFieldPreviewFilled() {
    AppTheme {
        Surface {
            CashierSearchTextField(
                value = "Sample search query",
                onValueChange = {},
                onImeAction = {},
            )
        }
    }
}

@Preview(showBackground = true, name = "CashierSearchTextField - Disabled")
@Composable
private fun CashierSearchTextFieldPreviewDisabled() {
    AppTheme {
        Surface {
            CashierSearchTextField(
                value = "",
                onValueChange = {},
                onImeAction = {},
                enabled = false,
            )
        }
    }
}