package com.fajarxfce.feature.splash.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajarxfce.core.ui.extension.boldBorder
import com.fajarxfce.core.ui.extension.collectWithLifecycle
import com.fajarxfce.core.ui.theme.CashierAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import com.fajarxfce.feature.splash.ui.R
@Composable
internal fun SplashScreen(
    uiEffect: Flow<SplashContract.UiEffect>,
    onNavigateToWelcome: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            SplashContract.UiEffect.NavigateWelcome -> onNavigateToWelcome()
            SplashContract.UiEffect.NavigateHome -> onNavigateToHome()
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {

        Column(
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(24.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(144.dp)
                    .clip(CircleShape)
                    .boldBorder(100),
                imageVector = CashierAppTheme.icons.logo,
                tint = Color.Unspecified,
                contentDescription = null,
            )
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() +
                        scaleIn()
            ) {
                Text(
                    text = stringResource(com.fajarxfce.core.ui.R.string.core_ui_app_name),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .width(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp),
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun SplashScreenPreview() {
    SplashScreen(
        uiEffect = emptyFlow(),
        onNavigateToWelcome = { },
        onNavigateToHome = { }
    )
}
