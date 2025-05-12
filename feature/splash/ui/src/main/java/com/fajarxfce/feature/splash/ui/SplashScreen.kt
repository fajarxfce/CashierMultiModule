package com.fajarxfce.feature.splash.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajarxfce.core.ui.component.CashierAppText
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
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = CashierAppTheme.colors.background,
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = com.fajarxfce.core.ui.R.drawable.core_ui_logo_new),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 46.dp),
            )
            Spacer(modifier = Modifier.height(34.dp))
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() +
                        scaleIn()
            ) {
                CashierAppText(
                    text = stringResource(id = com.fajarxfce.core.ui.R.string.core_ui_app_name),
                    style = CashierAppTheme.typography.heading1,
                    color = CashierAppTheme.colors.onBackground,
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            Box(
                modifier = Modifier
                    .width(300.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp),
                    trackColor = CashierAppTheme.colors.lightBlue,
                    color = CashierAppTheme.colors.blue
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            CashierAppText(
                text = stringResource(id = R.string.feature_splash_ui_loading),
                style = CashierAppTheme.typography.paragraph1,
                color = CashierAppTheme.colors.onBackground,
                textAlign = TextAlign.Center,
            )
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
