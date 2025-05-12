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
                title = { Text("Login") },
                navigationIcon = {
                    IconButton(onClick = { onAction(LoginContract.UiAction.OnBackClick) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Logo or app title
                Text(
                    text = "Welcome Back",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Sign in to continue",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                // Email field
                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = { onAction(LoginContract.UiAction.OnEmailChange(it)) },
                    label = { Text("Email") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    singleLine = true
                )

                // Password field
                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = { onAction(LoginContract.UiAction.OnPasswordChange(it)) },
                    label = { Text("Password") },
                    leadingIcon = { Icon(Icons.Default.Password, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            onAction(LoginContract.UiAction.OnLoginClick)
                        }
                    ),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (passwordVisible) "Hide password" else "Show password"
                            )
                        }
                    },
                    singleLine = true
                )

                // Forgot password link
                TextButton(
                    onClick = { onAction(LoginContract.UiAction.OnForgotPasswordClick) },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(bottom = 24.dp)
                ) {
                    Text("Forgot Password?")
                }

                // Login button
                Button(
                    onClick = { onAction(LoginContract.UiAction.OnLoginClick) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = MaterialTheme.shapes.medium,
                ) {
                    Text("Login")
                }

                // Register option
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don't have an account?",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    TextButton(onClick = { onAction(LoginContract.UiAction.OnRegisterClick) }) {
                        Text(
                            text = "Register",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            // Show loading indicator if needed
            if (uiState.isLoading) {
                LoadingIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            // Show dialog if needed
            uiState.dialogState?.let { dialogState ->
//                InfoDialog(
//                    dialogState = dialogState,
//                    onDismiss = { onAction(LoginContract.UiAction.OnDialogDismiss) }
//                )
            }
        }
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