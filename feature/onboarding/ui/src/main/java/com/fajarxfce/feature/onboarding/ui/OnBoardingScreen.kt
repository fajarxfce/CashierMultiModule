package com.fajarxfce.feature.onboarding.ui


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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

    LaunchedEffect(key1 = true) {
        uiEffect.collectLatest { effect ->
            when (effect) {
                UiEffect.NavigateLogin -> onNavigateToLogin()
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        OnBoardingContent(
            onClickNext = {  },
            onClickSkip = {  },
            onClickDone = {  }
        )

        // Show dialog if needed
        uiState.dialogState?.let { dialogState ->
            CashierDialog(
                message = dialogState.message ?: "",
                isSuccess = dialogState.isSuccess ?: false,
                onDismissRequest = {  }
            )
        }

        // Show loading if needed
        if (uiState.isLoading) {
            LoadingIndicator()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnBoardingContent(
    onClickNext: () -> Unit,
    onClickSkip: () -> Unit,
    onClickDone: () -> Unit
) {
    val pages = listOf(
        OnBoardingPage(
            title = "Welcome to Our App",
            description = "Your all-in-one solution for managing your business efficiently",
            imageRes = com.fajarxfce.core.ui.R.drawable.core_ui_ic_logo
        ),
        OnBoardingPage(
            title = "Track Your Sales",
            description = "Monitor your sales performance with detailed analytics and insights",
            imageRes = com.fajarxfce.core.ui.R.drawable.core_ui_ic_logo
        ),
        OnBoardingPage(
            title = "Manage Inventory",
            description = "Keep track of your inventory in real-time with smart notifications",
            imageRes = com.fajarxfce.core.ui.R.drawable.core_ui_ic_logo
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Skip button at the top right
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            this@Column.AnimatedVisibility(
                visible = pagerState.currentPage < pages.size - 1,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                TextButton(
                    onClick = onClickSkip,
                ) {
                    Text(
                        text = "Skip",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Pager with onboarding content
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            OnBoardingPage(
                page = pages[page],
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }

        // Page indicators
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pages.size) { iteration ->
                val color = if (pagerState.currentPage == iteration)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)

                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }

        // Bottom button area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            // Next button
            this@Column.AnimatedVisibility(
                visible = pagerState.currentPage < pages.size - 1,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            if (pagerState.currentPage < pages.size - 1) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                        onClickNext()
                    },
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Next",
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
                    )
                }
            }

            // Get Started button
            this@Column.AnimatedVisibility(
                visible = pagerState.currentPage == pages.size - 1,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Button(
                    onClick = onClickDone,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Get Started",
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun OnBoardingPage(
    page: OnBoardingPage,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .size(280.dp)
                .padding(bottom = 24.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Image(
                painter = painterResource(id = page.imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}

data class OnBoardingPage(
    val title: String,
    val description: String,
    val imageRes: Int
)

@Preview
@Composable
private fun OnBoardingScreenPreview() {
    OnBoardingScreen(
        uiState = OnBoardingContract.UiState(),
        uiEffect = emptyFlow(),
        onAction = { },
        onNavigateToLogin = { }
    )
}