package com.fajarxfce.shopping.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.fajarxfce.core.designsystem.component.CartSummaryBar
import com.fajarxfce.core.designsystem.component.DataCard
import com.fajarxfce.core.designsystem.component.ErrorItem
import com.fajarxfce.core.designsystem.component.ErrorScreen
import com.fajarxfce.core.designsystem.component.NiaOverlayLoadingWheel
import com.fajarxfce.core.designsystem.theme.AppTheme
import com.fajarxfce.core.designsystem.theme.dark_primaryContainer
import com.fajarxfce.core.designsystem.theme.dark_surface
import com.fajarxfce.core.model.data.product.Product
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import kotlin.text.append
import kotlin.text.firstOrNull
import kotlin.text.get

@Composable
fun ShoppingScreen(
    modifier: Modifier = Modifier,
    viewModel: ShoppingViewModel = hiltViewModel(),
    onProductClick: (Int) -> Unit = {},
    onViewCartClick: () -> Unit = {}
) {
    val uiState by viewModel.shoppingUiState.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()

    cartItems.forEach {
        Timber.d("Cart Item: ${it.key.name} - Quantity: ${it.value}")
    }

    ShoppingContent(
        uiState = uiState,
        cartItems = cartItems,
        onProductClick = onProductClick,
        onAddToCart = { product, quantity -> viewModel.addToCart(product, quantity) },
        onRemoveFromCart = { product, quantity -> viewModel.removeFromCart(product, quantity) },
        onViewCartClick = onViewCartClick,
        modifier = modifier
    )
}

@Composable
fun ShoppingContent(
    uiState: ShoppingUiState<Flow<PagingData<Product>>>,
    cartItems: Map<Product, Int>,
    onProductClick: (Int) -> Unit,
    onAddToCart: (Product, Int) -> Unit,
    onRemoveFromCart: (Product, Int) -> Unit,
    onViewCartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            is ShoppingUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    NiaOverlayLoadingWheel(
                        contentDesc = "Loading products...",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }

            is ShoppingUiState.Success -> {
                val pagingFlow = uiState.data
                if (pagingFlow != null) {
                    val pagingItems: LazyPagingItems<Product> = pagingFlow.collectAsLazyPagingItems()
                    val listState = rememberLazyGridState()

                    LazyVerticalGrid(
                        state = listState,
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            count = pagingItems.itemCount,
                            key = pagingItems.itemKey { product -> product.id!! }
                        ) { index ->
                            val product = pagingItems[index]
                            if (product != null) {
                                val quantityInCart = cartItems[product] ?: 0
                                DataCard(
                                    title = product.name ?: "",
                                    subtitle = "$${product.price}",
                                    imageUrl = product.media?.firstOrNull()?.originalUrl,
                                    quantity = quantityInCart,
                                    onIncreaseQuantity = { onAddToCart(product, 1) },
                                    onDecreaseQuantity = { onRemoveFromCart(product, 1) }
                                )
                            }
                        }

                        when (pagingItems.loadState.refresh) {
                            is LoadState.Loading -> {
                                item {
                                    NiaOverlayLoadingWheel(
                                        contentDesc = "Loading more products...",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                    )
                                }
                            }

                            is LoadState.Error -> {
                                item {
                                    ErrorItem(
                                        message = "Failed to load products",
                                        onRetry = { pagingItems.refresh() }
                                    )
                                }
                            }

                            else -> {}
                        }

                        when (pagingItems.loadState.append) {
                            is LoadState.Loading -> {
                                item {
                                    NiaOverlayLoadingWheel(
                                        contentDesc = "Loading more products...",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                    )
                                }
                            }

                            is LoadState.Error -> {
                                item {
                                    ErrorItem(
                                        message = "Failed to load more products",
                                        onRetry = { pagingItems.retry() }
                                    )
                                }
                            }

                            else -> {}
                        }
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize())
                }
            }

            is ShoppingUiState.Error -> {
                val error = (uiState as ShoppingUiState.Error).exception
                ErrorScreen(
                    message = error.message ?: "An error occurred",
                    onRetryClick = {  }
                )
            }
        }

        val itemCount = cartItems.values.sum()
        val totalPrice = cartItems.entries.sumOf {
            (it.key.price?.toDouble() ?: 0.0) * it.value.toDouble()
        }
        if (itemCount > 0) {
            CartSummaryBar(
                itemCount = itemCount,
                totalPrice = totalPrice,
                onViewCartClick = onViewCartClick
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun ShoppingScreenPreview() {
    AppTheme {
        ShoppingContent(
            uiState = ShoppingUiState.Loading,
            cartItems = emptyMap(),
            onProductClick = {},
            onAddToCart = { _, _ -> },
            onRemoveFromCart = { _, _ -> },
            onViewCartClick = {}
        )
    }
}
