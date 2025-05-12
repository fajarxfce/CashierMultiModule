package com.fajarxfce.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajarxfce.core.ui.theme.CashierAppTheme

@Composable
fun CashierTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isSingleLine: Boolean = true,
    isPassword: Boolean = false,
    icon: ImageVector? = null,
    onValueChange: (String) -> Unit,
) {
    val leadingIcon: @Composable (() -> Unit)? = icon?.let {
        {
            Icon(
                modifier = Modifier
                    .padding(16.dp)
                    .size(24.dp),
                imageVector = icon,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null,
            )
        }
    }

    var visibility by remember { mutableStateOf(false) }
    val visualTransformation =
        if (visibility) VisualTransformation.None else PasswordVisualTransformation()

    val trailingIcon: @Composable (() -> Unit)? = if (isPassword) {
        {
            Icon(
                modifier = Modifier
                    .padding(16.dp)
                    .size(24.dp),
                imageVector = if (visibility) CashierAppTheme.icons.visibilityOn else CashierAppTheme.icons.visibilityOff,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = label,
            )
        }
    } else {
        null
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        value = value,
        onValueChange = onValueChange,
        label = {
            CashierAppText(
                text = label,
                style = CashierAppTheme.typography.paragraph1,
                color = CashierAppTheme.colors.onBackground
            )
        },
        textStyle = CashierAppTheme.typography.paragraph1,
        singleLine = isSingleLine,
        maxLines = if (isSingleLine) 1 else Int.MAX_VALUE,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = CashierAppTheme.colors.background,
            focusedContainerColor = CashierAppTheme.colors.background,
            focusedLabelColor = CashierAppTheme.colors.blue,
            focusedBorderColor = CashierAppTheme.colors.blue,
            unfocusedLabelColor = CashierAppTheme.colors.onBackground.copy(alpha = 0.5f),
            unfocusedBorderColor = CashierAppTheme.colors.onBackground,
            cursorColor = CashierAppTheme.colors.onBackground
        ),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = if (isPassword) visualTransformation else VisualTransformation.None,
    )

}

@Preview(showBackground = true)
@Composable
private fun CashierTextFieldPreview() {
    Column {
        CashierTextField(
            value = "",
            label = "Email or Username",
            icon = CashierAppTheme.icons.email,
            isPassword = false,
            onValueChange = {},
        )
        Spacer(modifier = Modifier.height(16.dp))
        CashierTextField(
            value = "",
            label = "Password",
            icon = CashierAppTheme.icons.lock,
            isPassword = true,
            onValueChange = {},
        )
    }
}