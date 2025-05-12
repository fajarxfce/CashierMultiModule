package com.fajarxfce.feature.login.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajarxfce.core.ui.component.CashierAppText
import com.fajarxfce.core.ui.extension.collectWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
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
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    // Handle navigation effects
    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is LoginContract.UiEffect.NavigateToHome -> onNavigateToHome()
            is LoginContract.UiEffect.NavigateToRegister -> onNavigateToRegister()
            is LoginContract.UiEffect.NavigateBack -> onNavigateBack()
        }
    }

    // Bottom sheet for forgot password
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scrollState = rememberScrollState()

    if (uiState.isForgotPasswordSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                onAction(LoginContract.UiAction.OnForgotPasswordSheetDismiss)
            },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Reset Password",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Enter your email address and we'll send you a link to reset your password",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = { onAction(LoginContract.UiAction.OnEmailChange(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    )
                )
                Button(
                    onClick = { onAction(LoginContract.UiAction.OnSendPasswordResetEmailClick) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Send Reset Link")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // Main content
    Scaffold(
        topBar = {
            TopAppBar(
                title = {  },
                navigationIcon = {
                    IconButton(onClick = { onAction(LoginContract.UiAction.OnBackClick) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
    ) { paddingValues ->
        LoginContent(
            modifier = modifier
                .padding(paddingValues)
                .verticalScroll(scrollState),
            uiState = uiState,
            onEmailChange = { onAction(LoginContract.UiAction.OnEmailChange(it)) },
            onPasswordChange = { onAction(LoginContract.UiAction.OnPasswordChange(it)) },
            onLoginClick = { onAction(LoginContract.UiAction.OnLoginClick) },
            onForgotPasswordClick = { onAction(LoginContract.UiAction.OnForgotPasswordClick) },
            onRegisterClick = { onAction(LoginContract.UiAction.OnRegisterClick) }
        )
    }
}

@Composable
internal fun LoginContent(
    modifier: Modifier = Modifier,
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
            .padding(horizontal = 23.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CashierAppText(
            text = "Welcome",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        CashierAppText(
            text = "It's great to see you again",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(40.dp))

    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        uiState = LoginContract.UiState(),
        uiEffect = emptyFlow(),
        onAction = {},
        onNavigateBack = {},
        onNavigateToRegister = {},
        onNavigateToHome = {}
    )
}