package com.fajarxfce.core.ui.component.textfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fajarxfce.core.ui.component.CashierIcon
import com.fajarxfce.core.ui.theme.CashierBlue
import com.fajarxfce.core.ui.theme.CashierGray
import com.fajarxfce.core.ui.theme.CashierGreen

@Composable
fun CashierTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
) {
    var visibility by remember { mutableStateOf(false) }
    val visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation()
    val trailingIcon: @Composable (() -> Unit)? = if (isPassword) {
        {
            Icon(
                modifier = Modifier
                    .padding(16.dp)
                    .size(24.dp)
                    .clickable { visibility = !visibility },
                imageVector = if (visibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                tint = CashierBlue,
                contentDescription = null,
            )
        }
    } else {
        null
    }
    val focusManager = LocalFocusManager.current
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = modifier
            .fillMaxWidth(),
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "",
                tint = CashierBlue
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = CashierGray,
            unfocusedTextColor = CashierGray,
        ),
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 16.sp,
                color = CashierGray
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
        visualTransformation = if (isPassword) visualTransformation else VisualTransformation.None,
        trailingIcon = trailingIcon,
    )
}


@Preview(showBackground = true)
@Composable
fun PhoneNumberTextFieldPreview() {
    CashierTextField(
        value = "jhgh",
        onValueChange = {},
        icon = Icons.Filled.Phone,
        placeholder = "Phone Number",
        isPassword = true
    )
}

