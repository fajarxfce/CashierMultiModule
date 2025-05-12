package com.fajarxfce.feature.login.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajarxfce.core.ui.component.CashierAppText
import com.fajarxfce.core.ui.component.CashierButton
import com.fajarxfce.core.ui.component.CashierDialog
import com.fajarxfce.core.ui.component.CashierLoading
import com.fajarxfce.core.ui.component.CashierTextField
import com.fajarxfce.core.ui.component.DialogState
import com.fajarxfce.core.ui.extension.collectWithLifecycle
import com.fajarxfce.core.ui.extension.noRippleClickable
import com.fajarxfce.core.ui.theme.CashierAppTheme
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
    modifier: Modifier = Modifier
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
                title = {  },
                navigationIcon = {
                    IconButton(onClick = { onAction(LoginContract.UiAction.OnBackClick) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CashierAppTheme.colors.background,
                    titleContentColor = CashierAppTheme.colors.onBackground,
                    actionIconContentColor = CashierAppTheme.colors.onBackground
                )
            )
        },
        containerColor = CashierAppTheme.colors.background,
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

    if (uiState.dialogState != null) {
        CashierDialog(
            message = uiState.dialogState.message,
            isSuccess = uiState.dialogState.isSuccess,
            onDismissRequest = {
                if (uiState.dialogState.isSuccess == true){
                    onAction(LoginContract.UiAction.OnDialogDismiss)
                } else {
                    onAction(LoginContract.UiAction.OnDialogDismiss)
                }
            }
        )
    }

    if (uiState.isLoading){
        CashierLoading(
            modifier = Modifier
                .fillMaxSize()
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
            text = "Welcome!",
            style = CashierAppTheme.typography.heading1
        )
        Spacer(modifier = Modifier.height(8.dp))
        CashierAppText(
            text = "It's great to see you again",
            style = CashierAppTheme.typography.paragraph1,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(40.dp))
        CashierTextField(
            value = uiState.email,
            label = "Email",
            icon = CashierAppTheme.icons.email,
            isPassword = false,
            onValueChange = { onEmailChange(it) },
        )
        Spacer(modifier = Modifier.height(24.dp))
        CashierTextField(
            value = uiState.password,
            label = "Password",
            icon = CashierAppTheme.icons.lock,
            isPassword = true,
            onValueChange = { onEmailChange(it) },
        )
        Spacer(modifier = Modifier.height(24.dp))
        CashierAppText(
            modifier = Modifier
                .align(Alignment.End)
                .noRippleClickable { onForgotPasswordClick() },
            text = "Forgot Password?",
            style = CashierAppTheme.typography.heading6,
            color = CashierAppTheme.colors.blue
        )
        Spacer(modifier = Modifier.height(40.dp))
        CashierButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Login",
            onClick = { onLoginClick() },
        )
        Spacer(modifier = Modifier.height(24.dp))
        CashierAppText(
            fullText = stringResource(R.string.feature_login_ui_don_t_have_an_account_full_text),
            spanTexts = listOf(stringResource(R.string.feature_login_ui_don_t_have_account_span)),
            style = CashierAppTheme.typography.paragraph2,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(40.dp))
        CashierAppText(
            fullText = stringResource(R.string.feature_login_ui_policy),
            spanTexts = listOf(
                stringResource(R.string.feature_login_ui_terms_of_service_span),
                stringResource(R.string.feature_login_ui_privacy_policy_span),
            ),
            style = CashierAppTheme.typography.paragraph2,
            textAlign = TextAlign.Center,
        )
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

@Preview
@Composable
private fun LoginScreenLoadingPreview() {
    LoginScreen(
        uiState = LoginContract.UiState(
            isLoading = true,
            email = "admin@email.com"),
        uiEffect = emptyFlow(),
        onAction = {},
        onNavigateBack = {},
        onNavigateToRegister = {},
        onNavigateToHome = {}
    )
}

@Preview
@Composable
private fun LoginScreenDialogPreview() {
    LoginScreen(
        uiState = LoginContract.UiState(
            email = "admin@email.com",
            isLoading = false,
            dialogState = DialogState(
                message = "This is a dialog message",
                isSuccess = true
            )
            ),
        uiEffect = emptyFlow(),
        onAction = {},
        onNavigateBack = {},
        onNavigateToRegister = {},
        onNavigateToHome = {}
    )
}