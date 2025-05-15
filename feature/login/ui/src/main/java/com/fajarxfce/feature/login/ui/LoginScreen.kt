package com.fajarxfce.feature.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajarxfce.core.ui.component.CashierText
import com.fajarxfce.core.ui.component.CashierTextH5Text
import com.fajarxfce.core.ui.component.button.BigActionButtonCompose
import com.fajarxfce.core.ui.component.dialog.CashierDialog
import com.fajarxfce.core.ui.component.textfield.CashierTextField
import com.fajarxfce.core.ui.extension.collectWithLifecycle
import com.fajarxfce.core.ui.extension.noRippleClickable
import com.fajarxfce.core.ui.theme.AppTheme
import com.fajarxfce.core.ui.theme.CashierBlue
import com.fajarxfce.core.ui.theme.CashierGray
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun LoginScreen(
    uiState: LoginContract.UiState,
    uiEffect: Flow<LoginContract.UiEffect>,
    onAction: (LoginContract.UiAction) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is LoginContract.UiEffect.NavigateToHome -> onNavigateToHome()
            is LoginContract.UiEffect.NavigateToRegister -> onNavigateToRegister()
            is LoginContract.UiEffect.NavigateBack -> onNavigateBack()
        }
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { onAction(LoginContract.UiAction.OnBackClick) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground,
                ),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets.navigationBars,

        ) { paddingValues ->
        LoginContent(
            uiState = uiState,
            onEmailChange = { onAction(LoginContract.UiAction.OnEmailChange(it)) },
            onPasswordChange = { onAction(LoginContract.UiAction.OnPasswordChange(it)) },
            onLoginClick = { onAction(LoginContract.UiAction.OnLoginClick) },
            onForgotPasswordClick = { onAction(LoginContract.UiAction.OnForgotPasswordClick) },
            onRegisterClick = { onAction(LoginContract.UiAction.OnRegisterClick) },
            modifier = Modifier
                .padding(paddingValues),
        )
    }

    if (uiState.dialogState != null) {
        CashierDialog(
            onDismissRequest = {
                if (uiState.dialogState.isSuccess == true) {
                    onAction(LoginContract.UiAction.OnDialogDismiss)
                } else {
                    onAction(LoginContract.UiAction.OnDialogDismiss)
                }
            },
            isSuccess = uiState.dialogState.isSuccess,
            onConfirm = { onAction(LoginContract.UiAction.OnDialogDismiss) },
        )
    }

    if (uiState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                ) {},
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(
                color = CashierBlue,
                strokeCap = StrokeCap.Round,
            )
        }
    }
}

@Composable
internal fun LoginContent(
    modifier: Modifier,
    uiState: LoginContract.UiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onRegisterClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CashierTextH5Text(
            text = "Sign In",
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        CashierText(
            text = "Enter your email and password to sign in into your account",
            color = CashierGray,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(24.dp))
        CashierTextField(
            value = uiState.email,
            onValueChange = { onEmailChange(it) },
            placeholder = "Enter your email",
            icon = Icons.Default.Email,
            keyboardType = KeyboardType.Email,
        )
        Spacer(modifier = Modifier.height(8.dp))
        CashierTextField(
            value = uiState.password,
            onValueChange = { onPasswordChange(it) },
            placeholder = "Enter your password",
            icon = Icons.Default.Lock,
            isPassword = true,
        )
        Spacer(modifier = Modifier.height(8.dp))
        CashierText(
            modifier = Modifier
                .align(Alignment.End)
                .noRippleClickable { onForgotPasswordClick() },
            text = "Forgot Password?",
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.height(34.dp))
        BigActionButtonCompose(
            buttonText = "Sign In",
            onClick = onLoginClick,
        )
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    AppTheme {
        LoginScreen(
            uiState = LoginContract.UiState(),
            uiEffect = emptyFlow(),
            onAction = {},
            onNavigateBack = {},
            onNavigateToRegister = {},
            onNavigateToHome = {},
        )
    }
}

@Preview
@Composable
private fun LoginScreenLoadingPreview() {
    AppTheme {
        LoginScreen(
            uiState = LoginContract.UiState(
                isLoading = true,
                email = "admin@email.com",
            ),
            uiEffect = emptyFlow(),
            onAction = {},
            onNavigateBack = {},
            onNavigateToRegister = {},
            onNavigateToHome = {},
        )
    }
}

@Preview
@Composable
private fun LoginScreenDialogPreview() {
    AppTheme {
        LoginScreen(
            uiState = LoginContract.UiState(
                email = "admin@email.com",
                isLoading = false,
            ),
            uiEffect = emptyFlow(),
            onAction = {},
            onNavigateBack = {},
            onNavigateToRegister = {},
            onNavigateToHome = {},
        )
    }
}