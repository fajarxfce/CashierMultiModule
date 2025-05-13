package com.fajarxfce.feature.onboarding.ui


import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajarxfce.core.ui.component.CashierText
import com.fajarxfce.core.ui.extension.collectWithLifecycle
import com.fajarxfce.feature.onboarding.ui.OnBoardingContract.UiEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun OnBoardingScreen(
    uiState: OnBoardingContract.UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (OnBoardingContract.UiAction) -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is UiEffect.NavigateToEmailLogin -> onNavigateToLogin()
            is UiEffect.NavigateToSignUp -> {}
            is UiEffect.NavigateToGoogleLogin -> onNavigateToLogin()
            is UiEffect.ShowDialog -> {}
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets.safeGestures,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // Logo
            Image(
                painter = painterResource(id = com.fajarxfce.core.ui.R.drawable.core_ui_logo_new),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 46.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            // App Name
            Text(
                text = "Cashier",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // App Jargon/Tagline
            CashierText(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Google Login Button
            Button(
                onClick = { onAction(OnBoardingContract.UiAction.OnLoginWithGoogleClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                // You can add Google icon here if available
                CashierText(
                    text = "Login with Google",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            // OR Divider
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                CashierText(
                    text = "OR",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Email Login Button
            OutlinedButton(
                onClick = { onAction(OnBoardingContract.UiAction.OnLoginWithGoogleClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                border = ButtonDefaults.outlinedButtonBorder
            ) {
                CashierText(
                    text = "Login with Email",
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CashierText(text = "Don't have an account? ")
                    TextButton(onClick = {}) {
                        CashierText(
                            text = "Sign Up",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            CashierText(
                text = stringResource(R.string.feature_onboarding_ui_policy),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}
@Preview
@Composable
private fun OnBoardingScreenPreview() {
    OnBoardingScreen(
        uiState = OnBoardingContract.UiState(),
        uiEffect = emptyFlow(),
        onAction = {},
        onNavigateToLogin = {},
    )
}