package com.fajarxfce.shopping.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.fajarxfce.core.designsystem.component.DataCard
import com.fajarxfce.core.designsystem.component.ErrorItem
import com.fajarxfce.core.designsystem.component.ErrorScreen
import com.fajarxfce.core.designsystem.component.LoadingScreen
import com.fajarxfce.core.designsystem.component.NiaOverlayLoadingWheel
import com.fajarxfce.core.designsystem.component.ShoppingTopBar
import com.fajarxfce.core.designsystem.theme.AppTheme
import com.fajarxfce.core.model.data.product.MediaItem
import com.fajarxfce.core.model.data.product.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingScreen(
    modifier: Modifier = Modifier,
    viewModel: ShoppingViewModel = hiltViewModel(),
    onProductClick: (Int) -> Unit = {},
    onViewCartClick: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()

    val pullRefreshState = rememberPullToRefreshState()

    when (val state = uiState.productsState) {
        is ShoppingUiState.Loading -> LoadingExperience({ viewModel.refreshProducts() })
        is ShoppingUiState.Error -> ErrorExperience(
            state.exception,
            { viewModel.refreshProducts() },
        )

        is ShoppingUiState.Success -> SuccessExperience(
            productsFlow = state.data,
            cartItems = cartItems,
            onProductClick = onProductClick,
            onAddToCart = { product, quantity -> viewModel.addToCart(product, quantity) },
            onRemoveFromCart = { product, quantity -> viewModel.removeFromCart(product, quantity) },
            onViewCartClick = onViewCartClick,
            onRefresh = { viewModel.refreshProducts() },
            isRefreshing = uiState is ShoppingUiState.Loading,
            modifier = modifier,
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuccessExperience(
    productsFlow: Flow<PagingData<Product>>,
    cartItems: Map<Product, Int>,
    onProductClick: (Int) -> Unit,
    onAddToCart: (Product, Int) -> Unit,
    onRemoveFromCart: (Product, Int) -> Unit,
    onViewCartClick: () -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val refreshState = rememberPullToRefreshState()
    val pagingItems = productsFlow.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            ShoppingTopBar(
                scrollBehavior = scrollBehavior,
                onViewCartClick = onViewCartClick,
                onSearchClick = { /* Implement search functionality */ },
                cartItemCount = cartItems.values.sum(),
            )
        },
        bottomBar = {
            if (cartItems.isNotEmpty()) {
                CartSummaryButton(
                    itemCount = cartItems.values.sum(),
                    totalAmount = cartItems.entries.sumOf { (product, quantity) ->
                        product.price?.toDouble()?.times(quantity) ?: 0.0
                    },
                    onClick = onViewCartClick
                )
            }
        }
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            state = refreshState,
            indicator = {
                Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = isRefreshing,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    state = refreshState,
                )
            },
        ) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .padding(paddingValues),
            ) {
                ProductGrid(
                    pagingItems = pagingItems,
                    cartItems = cartItems,
                    onProductClick = onProductClick,
                    onAddToCart = onAddToCart,
                    onRemoveFromCart = onRemoveFromCart,
                )
            }
        }
    }
}

@Composable
private fun CartSummaryButton(
    itemCount: Int,
    totalAmount: Double,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "View cart",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                if (itemCount > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 4.dp, y = (-4).dp)
                            .size(16.dp)
                            .background(MaterialTheme.colorScheme.error, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (itemCount > 9) "9+" else itemCount.toString(),
                            color = MaterialTheme.colorScheme.onError,
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp,
                        )
                    }
                }
            }

            Text(
                text = "$itemCount ${if (itemCount == 1) "item" else "items"} • $${String.format("%.2f", totalAmount)}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun ProductGrid(
    pagingItems: LazyPagingItems<Product>,
    cartItems: Map<Product, Int>,
    onProductClick: (Int) -> Unit,
    onAddToCart: (Product, Int) -> Unit,
    onRemoveFromCart: (Product, Int) -> Unit,
) {
    val listState = rememberLazyGridState()

    LazyVerticalGrid(
        state = listState,
        columns = GridCells.Adaptive(minSize = 160.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey { product -> product.id!! },
        ) { index ->
            val product = pagingItems[index]
            if (product != null) {
                val quantityInCart = cartItems[product] ?: 0
                AnimatedProductCard(
                    product = product,
                    quantity = quantityInCart,
                    onProductClick = { onProductClick(product.id ?: 0) },
                    onIncreaseQuantity = { onAddToCart(product, 1) },
                    onDecreaseQuantity = { onRemoveFromCart(product, 1) },
                )
            }
        }

        // Handle loading states
        when (pagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        LoadingIndicator()
                    }
                }
            }

            is LoadState.Error -> {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    ErrorItem(
                        message = "Couldn't load products",
                        onRetry = { pagingItems.refresh() },
                    )
                }
            }

            else -> {}
        }

        // Handle pagination loading states
        when (pagingItems.loadState.append) {
            is LoadState.Loading -> {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        LoadingIndicator(isSmall = true)
                    }
                }
            }

            is LoadState.Error -> {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    ErrorItem(
                        message = "Couldn't load more products",
                        onRetry = { pagingItems.retry() },
                    )
                }
            }

            else -> {}
        }
    }
}

@Composable
private fun AnimatedProductCard(
    product: Product,
    quantity: Int,
    onProductClick: () -> Unit,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
) {
    DataCard(
        title = product.name ?: "",
        subtitle = "$${product.price}",
        imageUrl = product.media?.firstOrNull()?.originalUrl,
        quantity = quantity,
        onIncreaseQuantity = onIncreaseQuantity,
        onDecreaseQuantity = onDecreaseQuantity,
    )
}

@Composable
private fun LoadingIndicator(isSmall: Boolean = false) {
    NiaOverlayLoadingWheel(
        contentDesc = "Loading...",
        modifier = Modifier.size(if (isSmall) 30.dp else 48.dp),
    )
}

@Composable
private fun LoadingExperience(onRetry: () -> Unit) {
    LoadingScreen(message = "Discovering products for you...")
}

@Composable
private fun ErrorExperience(error: Throwable, onRetry: () -> Unit) {
    ErrorScreen(
        message = error.message ?: "Something went wrong. Please try again.",
        onRetryClick = onRetry,
    )
}
@Preview
@Composable
private fun ShoppingScreenPreview() {
    AppTheme {
        ShoppingScreen(
            onProductClick = {},
            onViewCartClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun SuccessExperiencePreview() {
    val sampleProduct = Product(
        id = 1,
        name = "Sample Product",
        price = 99,
        media = listOf(MediaItem(originalUrl = "https://picsum.photos/200"))
    )

    val sampleProducts = flowOf(
        PagingData.from(listOf(
            sampleProduct,
            sampleProduct.copy(id = 2, name = "Another Product", price = 343),
            sampleProduct.copy(id = 3, name = "Third Product", price = 3999)
        ))
    )

    val cartItems = mapOf(
        sampleProduct to 2,
        sampleProduct.copy(id = 2, name = "Another Product", price = 2999) to 1
    )

    AppTheme {
        SuccessExperience(
            productsFlow = sampleProducts,
            cartItems = cartItems,
            onProductClick = {},
            onAddToCart = { _, _ -> },
            onRemoveFromCart = { _, _ -> },
            onViewCartClick = {},
            onRefresh = {},
            isRefreshing = false
        )
    }
}

@Preview
@Composable
private fun AnimatedProductCardPreview() {
    val sampleProduct = Product(
        id = 1,
        name = "Sample Product",
        price = 1999,
        media = listOf(MediaItem(originalUrl = "https://picsum.photos/200"))
    )

    AppTheme {
        AnimatedProductCard(
            product = sampleProduct,
            quantity = 2,
            onProductClick = {},
            onIncreaseQuantity = {},
            onDecreaseQuantity = {}
        )
    }
}

@Preview
@Composable
private fun LoadingExperiencePreview() {
    AppTheme {
        LoadingExperience(onRetry = {})
    }
}

@Preview
@Composable
private fun ErrorExperiencePreview() {
    AppTheme {
        ErrorExperience(
            error = Exception("Network error occurred"),
            onRetry = {}
        )
    }
}