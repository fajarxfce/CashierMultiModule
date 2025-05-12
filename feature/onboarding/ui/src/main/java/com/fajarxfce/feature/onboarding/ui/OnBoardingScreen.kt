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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fajarxfce.core.ui.component.CashierDialog
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
    uiEffect.collectWithLifecycle{ effect ->
        when (effect) {
            is UiEffect.NavigateToEmailLogin -> onNavigateToLogin()
            is UiEffect.NavigateToSignUp -> {}
            is UiEffect.NavigateToGoogleLogin -> onNavigateToLogin()
            is UiEffect.ShowDialog -> {}
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo
            Image(
                painter = painterResource(id = com.fajarxfce.core.ui.R.drawable.core_ui_ic_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(100.dp)
                    .padding(top = 46.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // App Name
            Text(
                text = "App Name",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // App Jargon/Tagline
            Text(
                text = "Your app's jargon or tagline goes here",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Google Login Button
            Button(
                onClick = { onAction(OnBoardingContract.UiAction.OnLoginWithGoogleClick) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = com.fajarxfce.core.ui.R.drawable.core_ui_ic_google),
                        contentDescription = "Google Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Text("Login with Google")
                }
            }

            // OR Divider
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                )
                Text(
                    text = "OR",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                )
            }

            // Email Login Button
            OutlinedButton(
                onClick = { onAction(OnBoardingContract.UiAction.OnLoginWithEmailClick) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Login with Email")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sign Up Text
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Don't have an account? ")
                Text(
                    text = "Sign up",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable {
                            onAction(OnBoardingContract.UiAction.OnSignUpClick)
                        }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Terms and Conditions
            Text(
                text = "By continuing, you agree to our Terms of Service and Privacy Policy",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 24.dp)
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
        onNavigateToLogin = {}
    )
}