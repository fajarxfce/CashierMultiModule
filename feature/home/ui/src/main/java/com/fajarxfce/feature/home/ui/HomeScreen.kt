package com.fajarxfce.feature.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fajarxfce.core.ui.theme.AppTheme
import com.fajarxfce.core.ui.theme.CashierBlue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
internal fun HomeScreen(
    uiState: HomeContract.UiState,
    uiEffect: Flow<HomeContract.UiEffect>,
    onAction: (HomeContract.UiAction) -> Unit,
    onNavigateDetail: (Int) -> Unit,
) {
    val primaryBlue = Color(0xFF0057CC)
    val lightBlue = Color(0xFFE6EFFD)
    val accentGreen = Color(0xFF35C2AA)
    val accentBlue = Color(0xFF3564C2)

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar()
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(
                        bottomStart = 24.dp,
                        bottomEnd = 24.dp,
                    ),
                    colors = CardDefaults.cardColors(containerColor = CashierBlue),
                ) {
                    CashierIllustration()
                    // Extra space to accommodate the overlapping MenuGrid
                    Spacer(modifier = Modifier.height(60.dp))
                }
                Spacer(modifier = Modifier.weight(1f))
            }

            // Position MenuGrid on top of the Card with offset
            MenuGrid(
                accentGreen = accentGreen,
                accentBlue = accentBlue,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                    )
                    .offset(y = 260.dp),  // Adjust this value to control overlap
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = CashierBlue,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(
                        text = "Mama Pizzado",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Outlet 1",
                            color = Color.White,
                            fontSize = 12.sp,
                        )
                        Text(
                            text = " > ",
                            color = Color.White,
                            fontSize = 12.sp,
                        )
                        Text(
                            text = "Cab.Bandung",
                            color = Color.White,
                            fontSize = 12.sp,
                        )
                    }
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description",
                )
            }
        },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Localized description",
                    tint = Color.White,
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

@Composable
fun CashierIllustration() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp),
    ) {
        // House shape illustration
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .aspectRatio(1.5f),
        ) {
            // House outline
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 24.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 16.dp,
                            bottomEnd = 16.dp,
                        ),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                // House roof line
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                ) {
                    Divider(
                        color = Color.White,
                        thickness = 2.dp,
                        modifier = Modifier.align(Alignment.TopCenter),
                    )
                }

                // Cashier illustration with counter
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Image(
                        painter = painterResource(id = com.fajarxfce.core.ui.R.drawable.core_ui_logo_new),
                        contentDescription = "Cashier",
                        modifier = Modifier
                            .size(400.dp)
                            .align(Alignment.Center),
                    )
                }
            }
        }
    }
}

@Composable
fun MenuGrid(accentGreen: Color, accentBlue: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(Color.Transparent)
            .padding(16.dp),
    ) {
        // First row of menu items
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            MenuCard(
                title = "Point of Sale\nPOS",
                iconRes = com.fajarxfce.core.ui.R.drawable.core_ui_ic_logo,
                backgroundColor = Color.White,
                contentColor = Color.Black,
                modifier = Modifier.weight(1f),
            )

            MenuCard(
                title = "Kelola\nProduk",
                iconRes = com.fajarxfce.core.ui.R.drawable.core_ui_ic_google,
                backgroundColor = Color.White,
                contentColor = Color.Black,
                modifier = Modifier.weight(1f),
            )
        }

        // Second row of menu items
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            MenuCard(
                title = "Laporan\nPenjualan",
                iconRes = com.fajarxfce.core.ui.R.drawable.core_ui_ic_google,
                backgroundColor = accentGreen,
                contentColor = Color.White,
                modifier = Modifier.weight(1f),
            )

            MenuCard(
                title = "Riwayat\nTransaksi",
                iconRes = com.fajarxfce.core.ui.R.drawable.core_ui_ic_google,
                backgroundColor = accentBlue,
                contentColor = Color.White,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
fun MenuCard(
    title: String,
    iconRes: Int,
    backgroundColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = modifier
            .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
        interactionSource = interactionSource,
        onClick = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            // Icon placeholder in the corner with custom shape
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        if (backgroundColor == Color.White) Color(0xFFEEEEEE) else backgroundColor.copy(
                            alpha = 0.7f,
                        ),
                    )
                    .padding(8.dp),
                contentAlignment = Alignment.Center,
            ) {
                // In a real app, you would use an actual icon resource
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = contentColor,
                )
            }

            // Title text
            Text(
                text = title,
                color = contentColor,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMamaPizzadoHomeScreen() {
    AppTheme {
        HomeScreen(
            uiState = HomeContract.UiState(),
            uiEffect = emptyFlow(),
            onAction = {},
            onNavigateDetail = {},
        )
    }
}