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
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.fajarxfce.core.designsystem.theme.AppTheme
import com.fajarxfce.core.designsystem.theme.dark_primaryContainer
import com.fajarxfce.core.designsystem.theme.dark_surface
import com.fajarxfce.core.model.data.product.Product
import kotlin.text.get


@Composable
fun ShoppingScreen(
    modifier: Modifier = Modifier,
    viewModel: ShoppingViewModel = hiltViewModel(),
    onProductClick: (Int) -> Unit = {},
    onAddToCart: (String, Int) -> Unit = { _, _ -> },
) {
    val uiState by viewModel.shoppingUiState.collectAsState()


    Column(modifier = modifier.fillMaxSize()) {
        // Sort controls could be added here

        when (uiState) {
            is ShoppingUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is ShoppingUiState.Success -> {
                // Extract the paging flow from viewModel for LazyPagingItems
                val pagingFlow = viewModel.getProductPagingFlow()
                val listState = rememberLazyGridState()

                pagingFlow?.let {
                    val pagingItems = it.collectAsLazyPagingItems()

                    LazyVerticalGrid (
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
                                ProductCard(
                                    product = product,
                                    onQuantityChange = { quantity ->
                                        // Handle add to cart action
                                    }
                                )
                            }
                        }

                        // Handle loading states
                        when (pagingItems.loadState.refresh) {
                            is LoadState.Loading -> {
                                item {
                                    LoadingItem(Modifier.fillMaxWidth())
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

                        // Append loading state (pagination)
                        when (pagingItems.loadState.append) {
                            is LoadState.Loading -> {
                                item {
                                    LoadingItem(Modifier.fillMaxWidth())
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
                }
            }

            is ShoppingUiState.Error -> {
                val error = (uiState as ShoppingUiState.Error).exception
                ErrorScreen(
                    message = error.message ?: "An error occurred",
                    onRetryClick = { viewModel.refreshProducts() }
                )
            }
        }
    }
}

@Composable
fun CartSummaryBar(
    itemCount: Int,
    totalPrice: Double,
    onViewCartClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("$itemCount items: $${String.format("%.2f", totalPrice)}")

        Button(onClick = onViewCartClick) {
            Text("View Cart")
        }
    }
}

@Composable
fun LoadingItem(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
@Composable
fun ErrorScreen(
    message: String,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message)
        Spacer(modifier = Modifier.height(8.dp))
        androidx.compose.material3.Button(onClick = onRetryClick) {
            Text("Retry")
        }
    }
}

@Composable
fun ErrorItem(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}
@Composable
fun ProductCard(
    product: Product,
    onQuantityChange: (Int) -> Unit,
) {
    var quantity by remember { mutableIntStateOf(0) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column {
            AsyncImage(
                model = product.media?.firstOrNull()?.originalUrl,
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(dark_primaryContainer),
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(dark_surface)
                    .padding(12.dp),
            ) {
                Text(
                    text = product.name ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = {
                            if (quantity > 0) {
                                quantity--
                                onQuantityChange(quantity)
                            }
                        },
                        enabled = quantity > 0,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Decrease",
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }

                    Text(
                        text = quantity.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                    )

                    IconButton(
                        onClick = {
                            quantity++
                            onQuantityChange(quantity)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increase",
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShoppingScreenPreview() {
    AppTheme {
        ShoppingScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun CartSummaryBarPreview() {
    AppTheme {
        CartSummaryBar(
            itemCount = 5,
            totalPrice = 100.0,
            onViewCartClick = {}
        )
    }
}
