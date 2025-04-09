package com.fajarxfce.feature.auth.ui

import android.graphics.drawable.Icon
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Password
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajarxfce.core.designsystem.component.NiaLoadingWheel
import com.fajarxfce.core.designsystem.theme.AppTheme

@Composable
fun RegisterRoute(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    RegisterScreen(
        onRegisterSuccess = onRegisterSuccess,
        onNavigateToLogin = onNavigateToLogin,
        modifier = modifier,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    // Form state
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var showConfirmPassword by rememberSaveable { mutableStateOf(false) }

    // Validation state
    var isNameError by rememberSaveable { mutableStateOf(false) }
    var isEmailError by rememberSaveable { mutableStateOf(false) }
    var isPasswordError by rememberSaveable { mutableStateOf(false) }
    var isConfirmPasswordError by rememberSaveable { mutableStateOf(false) }
    var isLoading by rememberSaveable { mutableStateOf(false) }

    // Error messages
    var nameErrorMessage by rememberSaveable { mutableStateOf("") }
    var emailErrorMessage by rememberSaveable { mutableStateOf("") }
    var passwordErrorMessage by rememberSaveable { mutableStateOf("") }
    var confirmPasswordErrorMessage by rememberSaveable { mutableStateOf("") }

    val validateForm = {
        isNameError = name.length < 3
        nameErrorMessage = if (isNameError) "Name must be at least 3 characters" else ""

        isEmailError = !email.contains('@') || email.length < 5
        emailErrorMessage = if (isEmailError) "Please enter a valid email address" else ""

        isPasswordError = password.length < 8
        passwordErrorMessage = if (isPasswordError) "Password must be at least 8 characters" else ""

        isConfirmPasswordError = password != confirmPassword
        confirmPasswordErrorMessage = if (isConfirmPasswordError) "Passwords don't match" else ""

        !isNameError && !isEmailError && !isPasswordError && !isConfirmPasswordError
    }

    val handleRegister = {
        if (validateForm()) {
            keyboardController?.hide()
            // Simulating registration process
            isLoading = true
            // In a real app, you would call your viewModel method here
            // For demo, we'll use a LaunchedEffect with delay
        }
    }

    // Simulate API call
    LaunchedEffect(isLoading) {
        if (isLoading) {
            // Simulate network delay
            kotlinx.coroutines.delay(2000)
            isLoading = false
            onRegisterSuccess()
        }
    }

    AppTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            modifier = modifier.fillMaxSize()
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Create Account",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Sign up to get started",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Name field
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                            if (isNameError) validateForm()
                        },
                        label = { Text("Full Name") },
                        placeholder = { Text("Enter your full name") },
                        isError = isNameError,
                        supportingText = {
                            if (isNameError) {
                                Text(nameErrorMessage)
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Person,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Email field
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            if (isEmailError) validateForm()
                        },
                        label = { Text("Email Address") },
                        placeholder = { Text("Enter your email") },
                        isError = isEmailError,
                        supportingText = {
                            if (isEmailError) {
                                Text(emailErrorMessage)
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Email,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password field
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            if (isPasswordError) validateForm()
                        },
                        label = { Text("Password") },
                        placeholder = { Text("Create a password") },
                        isError = isPasswordError,
                        supportingText = {
                            if (isPasswordError) {
                                Text(passwordErrorMessage)
                            }
                        },
                        visualTransformation = if (showPassword) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Password,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { showPassword = !showPassword }) {
                                Icon(
                                    imageVector = if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (showPassword) "Hide password" else "Show password"
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Confirm Password field
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            if (isConfirmPasswordError) validateForm()
                        },
                        label = { Text("Confirm Password") },
                        placeholder = { Text("Confirm your password") },
                        isError = isConfirmPasswordError,
                        supportingText = {
                            if (isConfirmPasswordError) {
                                Text(confirmPasswordErrorMessage)
                            }
                        },
                        visualTransformation = if (showConfirmPassword) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                                handleRegister()
                            }
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Password,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                                Icon(
                                    imageVector = if (showConfirmPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (showConfirmPassword) "Hide password" else "Show password"
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Register button
                    FilledIconButton(
                        onClick = handleRegister,
                        enabled = !isLoading && name.isNotEmpty() && email.isNotEmpty() &&
                                password.isNotEmpty() && confirmPassword.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AnimatedVisibility(visible = isLoading) {
                            NiaLoadingWheel(
                                modifier = Modifier.padding(end = 8.dp),
                                contentDesc = "Loading"
                            )
                        }
                        Text("REGISTER")
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Login navigation
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Already have an account?",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        TextButton(
                            onClick = onNavigateToLogin
                        ) {
                            Text("Login")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Row(
    verticalAlignment: Alignment.Vertical,
    content: @Composable () -> Unit
) {
    androidx.compose.foundation.layout.Row(
        verticalAlignment = verticalAlignment,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        content()
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    AppTheme {
        RegisterScreen(
            onRegisterSuccess = {},
            onNavigateToLogin = {}
        )
    }
}