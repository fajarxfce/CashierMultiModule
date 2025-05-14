package com.fajarxfce.feature.pos.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fajarxfce.core.ui.component.BaseTopAppBar
import com.fajarxfce.core.ui.component.CashierText
import com.fajarxfce.core.ui.component.CashierTextBody1
import com.fajarxfce.core.ui.component.CashierTextH5Text
import com.fajarxfce.core.ui.component.textfield.CashierSearchTextField
import com.fajarxfce.core.ui.theme.AppTheme
import com.fajarxfce.core.ui.theme.CashierGray
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
internal fun PosScreen(
    modifier: Modifier = Modifier,
    uiState: PosContract.UiState,
    uiEffect: Flow<PosContract.UiEffect>,
    onAction: (PosContract.UiAction) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateDetail: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            BaseTopAppBar(
                toolbarTitle = "POS",
                backButtonAction = onNavigateBack,
            )
        },
    ) { innerPadding ->
        PosContent(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun PosContent(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(100) {
            ProductCard()
        }
    }
}

@Composable
fun ProductCard(modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = modifier
            .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
        interactionSource = interactionSource,
        onClick = {  },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Product image on the left
                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    // Placeholder for product image
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                        contentDescription = "Product Image",
                        modifier = Modifier.size(36.dp),
                        tint = Color.Gray
                    )
                }

                // Product details in a Box to enable absolute positioning
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Product name, price and category
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(end = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        CashierTextBody1(
                            text = "Indomie Ayam Goreng",
                            fontSize = 16.sp,
                        )
                        Text(
                            text = "Rp 150.000",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF1E88E5)
                        )
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 0.dp,
                            ),
                        ) {
                            Text(
                                modifier = Modifier.padding(
                                    horizontal = 8.dp,
                                ),
                                text = "Electronics",
                                fontSize = 12.sp,
                                color = Color.Gray,
                            )
                        }
                    }

                    // Stock information at bottom right
                    CashierText(
                        text = "Stock: 25",
                        fontSize = 12.sp,
                        color = CashierGray,
                        modifier = Modifier.align(Alignment.BottomEnd),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PosScreenPreview() {
    AppTheme {
        PosScreen(
            uiState = PosContract.UiState(),
            uiEffect = emptyFlow(),
            onAction = {},
            onNavigateBack = {},
            onNavigateDetail = {},
        )
    }
}