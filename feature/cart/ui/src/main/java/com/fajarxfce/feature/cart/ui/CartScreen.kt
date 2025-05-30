package com.fajarxfce.feature.cart.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.Flow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fajarxfce.core.ui.component.BaseTopAppBar
import com.fajarxfce.core.ui.component.CashierText
import com.fajarxfce.core.ui.theme.AppTheme
import com.fajarxfce.core.ui.theme.CashierBlue
import com.fajarxfce.feature.cart.domain.model.CartItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow

@Composable
internal fun CartScreen(
    uiState: CartContract.UiState,
    uiEffect: Flow<CartContract.UiEffect>,
    uiAction: (CartContract.UiAction) -> Unit,
    onNavigateBack: () -> Unit = {}
) {
    val snackbarHostState = SnackbarHostState()

    LaunchedEffect(key1 = true) {
        uiEffect.collectLatest { effect ->
            when (effect) {
                is CartContract.UiEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        withDismissAction = true,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            BaseTopAppBar(
                toolbarTitle = "Keranjang",
                backButtonAction = onNavigateBack
            )
        },
        bottomBar = {
            if (uiState.cartItems.isNotEmpty()) {
                CartBottomBar(
                    cartItems = uiState.cartItems,
                    onCheckout = { uiAction(CartContract.UiAction.OnCheckout) }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    LoadingView()
                }
                uiState.cartItems.isEmpty() -> {
                    EmptyCartView()
                }
                else -> {
                    CartContent(
                        cartItems = uiState.cartItems,
                        onUpdateQuantity = { a, _ ->
                                           },
                        onRemoveItem = { /* implement this later */ }
                    )
                }
            }
        }
    }
}

@Composable
private fun CartContent(
    cartItems: List<CartItem>,
    onUpdateQuantity: (CartItem, Int) -> Unit,
    onRemoveItem: (CartItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(cartItems) { item ->
            CartItemCard(
                item = item,
                onUpdateQuantity = onUpdateQuantity,
                onRemoveItem = onRemoveItem
            )
        }
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun CartItemCard(
    item: CartItem,
    onUpdateQuantity: (CartItem, Int) -> Unit,
    onRemoveItem: (CartItem) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.name)
                    .crossfade(true)
                    .build(),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                CashierText(
                    text = item.name!!,
                    style = MaterialTheme.typography.titleMedium,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Rp 20,000",
                    style = MaterialTheme.typography.bodyMedium,
                    color = CashierBlue
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { onUpdateQuantity(item, item.quantity!! - 1) },
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant,
                                RoundedCornerShape(8.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Kurangi",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Text(
                        text = item.quantity.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    IconButton(
                        onClick = { onUpdateQuantity(item, item.quantity!! + 1) },
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(8.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Tambah",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(onClick = { onRemoveItem(item) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Hapus",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CartBottomBar(
    cartItems: List<CartItem>,
    onCheckout: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CashierText(
                    text = "Total 0 item)",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Rp 500,000",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = CashierBlue
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onCheckout,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CashierBlue
                )
            ) {
                Text(
                    text = "Checkout",
                    modifier = Modifier.padding(vertical = 4.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = CashierBlue)
    }
}

@Composable
private fun EmptyCartView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.ShoppingCart,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        CashierText(
            text = "Keranjang Belanja Kosong",
            style = MaterialTheme.typography.titleLarge,
        )

        Spacer(modifier = Modifier.height(8.dp))

        CashierText(
            text = "Tambahkan produk ke keranjang untuk mulai berbelanja",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview
@Composable
private fun CartScreenPreview() {
    AppTheme {
        CartScreen(
            uiState = CartContract.UiState(
                isLoading = false,
                cartItems = listOf(
                    CartItem(productId = 1, name = "Product 1", quantity = 1),
                    CartItem(productId = 2, name = "Product 2", quantity = 2),
                ),
            ),
            uiEffect = emptyFlow(),
            uiAction = {},
            onNavigateBack = {},
        )
    }
}

@Preview
@Composable
private fun CartContentPreview() {
    AppTheme {
        CartContent(
            cartItems = listOf(
                CartItem(productId = 1, name = "Product 1", quantity = 1),
                CartItem(productId = 2, name = "Product 2", quantity = 2),
            ),
            onUpdateQuantity = { _, _ -> },
            onRemoveItem = {},
        )
    }
}

@Preview
@Composable
private fun CartItemCardPreview() {
    AppTheme {
        CartItemCard(
            item = CartItem(productId = 1, name = "Product 1", quantity = 1),
            onUpdateQuantity = { _, _ -> },
            onRemoveItem = {},
        )
    }
}

@Preview
@Composable
private fun CartBottomBarPreview() {
    CartBottomBar(
        cartItems = listOf(
            CartItem(productId = 1, name = "Product 1", quantity = 1),
            CartItem(productId = 2, name = "Product 2", quantity = 2)
        ),
        onCheckout = {}
    )
}

@Preview
@Composable
private fun LoadingViewPreview() {
    LoadingView()
}

@Preview
@Composable
private fun EmptyCartViewPreview() {
    EmptyCartView()
}