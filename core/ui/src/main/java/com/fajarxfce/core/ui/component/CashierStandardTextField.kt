package com.fajarxfce.core.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.fajarxfce.core.ui.theme.AppTheme
import com.fajarxfce.core.ui.theme.CashierBackground
import com.fajarxfce.core.ui.theme.CashierBlue
import com.fajarxfce.core.ui.theme.CashierGray
import com.fajarxfce.core.ui.theme.CashierLightGray

@Composable
fun CashierStandardTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    labelText: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions()
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            CashierText(
                text = placeholderText,
                color = CashierGray
            )
        },
        readOnly = readOnly,
        enabled = enabled,
        interactionSource = interactionSource,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = CashierBlue,
            unfocusedIndicatorColor = CashierLightGray,
            focusedTextColor = if (isSystemInDarkTheme()) {
                CashierBackground
            } else {
                Color.White
            },
            focusedPlaceholderColor = CashierGray,
            disabledPlaceholderColor = CashierGray
        ),
        keyboardActions = keyboardActions,
        label = {
            if (labelText != null) {
                CashierText(text = labelText)
            }
        }
    )
}

@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
private fun CashierStandardTextFieldPreview() {
    AppTheme {
        CashierStandardTextField(
            value = "value",
            onValueChange = {},
            placeholderText = "text"
        )
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
private fun CashierStandardTextFieldNightPreview() {
    AppTheme {
        CashierStandardTextField(
            value = "value",
            onValueChange = {},
            placeholderText = "text"
        )
    }
}