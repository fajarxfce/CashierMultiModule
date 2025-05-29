package com.fajarxfce.feature.pos.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.ShoppingCartCheckout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fajarxfce.core.ui.component.BaseTopAppBar
import com.fajarxfce.core.ui.component.textfield.CashierSearchTextField
import com.fajarxfce.core.ui.theme.CashierBlue
import com.fajarxfce.feature.pos.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PosScreen(
    uiState: PosContract.UiState,
    onAction: (PosContract.UiAction) -> Unit,
    uiEffect: Flow<PosContract.UiEffect>,
    onNavigateBack: () -> Unit,
) {
    val pagingItems: LazyPagingItems<Product> = uiState.productsFlow.collectAsLazyPagingItems()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            BaseTopAppBar(
                toolbarTitle = "Select Product",
                backButtonAction = onNavigateBack,
            )
        },
        contentColor = Color.White,
        content = { paddingValues ->
            PosContent(
                modifier = Modifier.padding(paddingValues),
                pagingItems = pagingItems,
                onProductClick = { productId ->
                    onAction(PosContract.UiAction.OnProductClick(productId))
                },
            )
        },
    )
}

@Composable
private fun PosContent(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<Product>,
    onProductClick: (Int) -> Unit,
) {
    val lazyListState = rememberLazyListState()

    Box(modifier = modifier.fillMaxSize()) {
        if (pagingItems.loadState.refresh is LoadState.Loading && pagingItems.itemCount == 0) {
            LoadingIndicator(Modifier.align(Alignment.Center))
        } else if (pagingItems.loadState.refresh is LoadState.Error && pagingItems.itemCount == 0) {
            val error = (pagingItems.loadState.refresh as LoadState.Error).error
            ErrorStateView(
                modifier = Modifier.align(Alignment.Center),
                message = error.localizedMessage
                    ?: "An unknown error occurred", // stringResource(id = R.string.unknown_error),
                onRetry = { pagingItems.retry() },
            )
        } else if (pagingItems.loadState.refresh is LoadState.NotLoading && pagingItems.itemCount == 0 && pagingItems.loadState.append.endOfPaginationReached) {
            EmptyStateView(
                modifier = Modifier.align(Alignment.Center),
                message = "No products found.",
            )
        } else {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    count = pagingItems.itemCount,
                    key = pagingItems.itemKey { it.id ?: -1 },
                ) { index ->
                    val product = pagingItems[index]
                    if (product != null) {
                        ProductItemCard(
                            product = product,
                            onClick = { product.id?.let { onProductClick(it) } },
                        )
                    } else {
                        Spacer(
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                        )
                    }
                }

                // Append loading state
                item {
                    if (pagingItems.loadState.append is LoadState.Loading) {
                        LoadingIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                        )
                    }
                }

                // Append error state
                item {
                    if (pagingItems.loadState.append is LoadState.Error) {
                        val error = (pagingItems.loadState.append as LoadState.Error).error
                        ErrorItemView(
                            message = error.localizedMessage ?: "Failed to load more products",
                            onRetry = { pagingItems.retry() },
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ProductItemCard(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.imageUrl.orEmpty())
                    .crossfade(true)
                    // .placeholder(R.drawable.placeholder_image)
                    // .error(R.drawable.error_image)
                    .build(),
                contentDescription = product.name ?: "Product Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name.orEmpty(),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = product.description.orEmpty(),
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Rp ${product.price?.toString() ?: "N/A"}", // Format harga sesuai kebutuhan
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = CashierBlue,
                )
            }
        }
    }
}

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = CashierBlue,
        )
    }
}

@Composable
fun EmptyStateView(
    message: String,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Outlined.ShoppingCartCheckout,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(72.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
fun ErrorStateView(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Outlined.CloudOff,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(72.dp),
            tint = MaterialTheme.colorScheme.error.copy(alpha = 0.8f),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        ) {
            Text("Retry", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Composable
fun ErrorItemView(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onErrorContainer,
            modifier = Modifier.weight(1f),
        )
        TextButton(onClick = onRetry) {
            Text("Retry", color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Preview(showBackground = true, name = "POS Screen - Populated")
@Composable
fun PosScreenPopulatedPreview() {
    MaterialTheme {
        PosScreen(
            uiState = PosContract.UiState(
                productsFlow = flowOf(
                    PagingData.from(
                        listOf(
                            Product(
                                1,
                                "Modern Coffee Table",
                                "Elegant wooden coffee table for your living room.",
                                250000,
                                "https://via.placeholder.com/150/DDDDDD/808080?Text=Product1",
                            ),
                            Product(
                                2,
                                "Wireless Headphones",
                                "Noise-cancelling over-ear headphones.",
                                750000,
                                "https://via.placeholder.com/150/CCCCCC/808080?Text=Product2",
                            ),
                            Product(
                                3,
                                "Smart Watch Pro",
                                "Feature-rich smartwatch with long battery life.",
                                1200000,
                                "https://via.placeholder.com/150/BBBBBB/808080?Text=Product3",
                            ),
                        ),
                    ),
                ),
            ),
            onAction = {},
            uiEffect = emptyFlow(),
            onNavigateBack = {},
        )
    }
}

@Preview(showBackground = true, name = "POS Screen - Empty State")
@Composable
fun PosScreenEmptyPreview() {
    MaterialTheme {
        PosScreen(
            uiState = PosContract.UiState(
                productsFlow = flowOf(PagingData.empty()),
            ),
            onAction = {},
            uiEffect = emptyFlow<PosContract.UiEffect>(),
            onNavigateBack = {},
        )
        EmptyStateView(message = "No products available for preview.")
    }
}

@Preview(showBackground = true, name = "POS Screen - Error State")
@Composable
fun PosScreenErrorPreview() {
    MaterialTheme {
        ErrorStateView(message = "Error loading products for preview.", onRetry = {})
    }
}

@Preview(showBackground = true, name = "Product Item Card")
@Composable
fun ProductItemCardPreview() {
    MaterialTheme {
        ProductItemCard(
            product = Product(
                1,
                "Preview Product",
                "This is a great product description that might be a bit long.",
                199000,
                "https://via.placeholder.com/150",
            ),
            onClick = {},
        )
    }
}