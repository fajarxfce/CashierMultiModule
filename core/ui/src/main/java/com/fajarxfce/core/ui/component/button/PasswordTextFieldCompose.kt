package com.fajarxfce.core.ui.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fajarxfce.core.ui.theme.CashierBlue
import com.fajarxfce.core.ui.theme.CashierGray

@Composable
fun PasswordTextFieldCompose(
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    placeholderText: String = stringResource(id = com.fajarxfce.core.ui.R.string.core_ui_title_password),
    password: String,
    onPasswordChange: (String) -> Unit,
    onImeAction: () -> Unit,
    isError: Boolean = false,
    errorMessage: @Composable (() -> Unit)? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var passwordVisibility by remember {
        mutableStateOf(false)
    }

    TextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp
            ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = "",
                tint = CashierBlue
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(
                text = placeholderText,
                fontSize = 16.sp,
                color = CashierGray
            )
        },
        isError = isError,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
                keyboardController?.hide()
            }
        ),
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            val image = if (passwordVisibility) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }
            CashierIconButton(
                imageVector = image,
                onClick = { passwordVisibility = !passwordVisibility }
            )
        }
    )
    errorMessage?.invoke()
}

@Preview
@Composable
private fun PasswordTextFieldComposePreview() {
    PasswordTextFieldCompose(
        password = "some password",
        onPasswordChange = {},
        onImeAction = {},
        isError = true,
        errorMessage = { Text(text = "Password is too short") }
    )
}
