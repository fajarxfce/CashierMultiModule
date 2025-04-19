package com.fajarxfce.shopping.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import com.fajarxfce.core.designsystem.component.LoadingScreen
import com.fajarxfce.core.designsystem.component.NiaOverlayLoadingWheel
import com.fajarxfce.core.designsystem.component.ShoppingTopBar
import com.fajarxfce.core.designsystem.theme.AppTheme
import com.fajarxfce.core.model.data.product.MediaItem
import com.fajarxfce.core.model.data.product.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.text.toDouble

@Composable
fun ShoppingScreen(
    modifier: Modifier = Modifier,
    viewModel: ShoppingViewModel = hiltViewModel(),
    onProductClick: (Int) -> Unit = {},
    onViewCartClick: () -> Unit = {},
) {
    val uiState by viewModel.shoppingUiState.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()

    cartItems.forEach {
        Timber.d("Cart Item: ${it.key.name} - Quantity: ${it.value}")
        Timber.d("Cart Item: ${cartItems.values.sum()}")

    }

    ShoppingContent(
        uiState = uiState,
        cartItems = cartItems,
        onProductClick = onProductClick,
        onAddToCart = { product, quantity -> viewModel.addToCart(product, quantity) },
        onRemoveFromCart = { product, quantity -> viewModel.removeFromCart(product, quantity) },
        onViewCartClick = onViewCartClick,
        modifier = modifier,
        isRefreshing = uiState is ShoppingUiState.Loading,
        onRefresh = { viewModel.refreshProducts() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingContent(
    uiState: ShoppingUiState<Flow<PagingData<Product>>>,
    cartItems: Map<Product, Int>,
    onProductClick: (Int) -> Unit,
    onAddToCart: (Product, Int) -> Unit,
    onRemoveFromCart: (Product, Int) -> Unit,
    onViewCartClick: () -> Unit,
    modifier: Modifier = Modifier,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val stateRefresh = rememberPullToRefreshState()

    // Bottom sheet configuration
    val bottomSheetState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        sheetContent = {
            CartSheetContent(
                cartItems = cartItems,
                onAddToCart = onAddToCart,
                onRemoveFromCart = onRemoveFromCart,
                onCheckout = {
                    onViewCartClick()
                    coroutineScope.launch {
                        bottomSheetState.bottomSheetState.partialExpand()
                    }
                },
            )
        },
        sheetPeekHeight = if (cartItems.isEmpty()) 0.dp else 72.dp,
        sheetDragHandle = null, // We'll handle this with our custom summary bar
        contentColor = MaterialTheme.colorScheme.background,
        containerColor = MaterialTheme.colorScheme.background,
        sheetContainerColor = MaterialTheme.colorScheme.background,
        sheetContentColor = MaterialTheme.colorScheme.background,
        topBar = {
            ShoppingTopBar(
                modifier = Modifier,
                scrollBehavior= scrollBehavior,
                onViewCartClick = onViewCartClick,
            )
        },
    ) { paddingValues ->
        // Main content as before
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            modifier = modifier,
            state = stateRefresh,
            indicator = {
                Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = isRefreshing,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    state = stateRefresh,
                )
            },
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when (uiState) {
                    is ShoppingUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            LoadingScreen(
                                message = "Loading products...",
                            )
                        }
                    }

                    is ShoppingUiState.Success -> {
                        val pagingFlow = uiState.data
                        val pagingItems: LazyPagingItems<Product> =
                            pagingFlow.collectAsLazyPagingItems()
                        val listState = rememberLazyGridState()

                        LazyVerticalGrid(
                            state = listState,
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            items(
                                count = pagingItems.itemCount,
                                key = pagingItems.itemKey { product -> product.id!! },
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
                                        onDecreaseQuantity = { onRemoveFromCart(product, 1) },
                                    )
                                }
                            }

                            when (pagingItems.loadState.refresh) {
                                is LoadState.Loading -> {
                                    item(span = { GridItemSpan(maxLineSpan) }) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 16.dp),
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            LoadingScreen(
                                                message = "",
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                            )
                                        }
                                    }
                                }

                                is LoadState.Error -> {
                                    item {
                                        ErrorItem(
                                            message = "Failed to load products",
                                            onRetry = { pagingItems.refresh() },
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
                                                .padding(16.dp),
                                        )
                                    }
                                }

                                is LoadState.Error -> {
                                    item {
                                        ErrorItem(
                                            message = "Failed to load more products",
                                            onRetry = { pagingItems.retry() },
                                        )
                                    }
                                }

                                else -> {}
                            }
                        }
                    }

                    is ShoppingUiState.Error -> {
                        val error = uiState.exception
                        ErrorScreen(
                            message = error.message ?: "An error occurred",
                            onRetryClick = { },
                        )
                    }
                }
            }
        }
    }

}

@Composable
private fun CartSheetContent(
    cartItems: Map<Product, Int>,
    onAddToCart: (Product, Int) -> Unit,
    onRemoveFromCart: (Product, Int) -> Unit,
    onCheckout: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Custom cart summary header that's always visible
        val itemCount = cartItems.values.sum()
        val totalPrice = cartItems.entries.sumOf {
            (it.key.price?.toDouble() ?: 0.0) * it.value.toDouble()
        }

        CartSummaryBar(
            itemCount = itemCount,
            totalPrice = totalPrice,
            onViewCartClick = onCheckout,
        )

        // Scrollable list of cart items
        if (itemCount > 0) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 8.dp),
            ) {
                items(
                    count = cartItems.size,
                    key = { index -> cartItems.keys.elementAt(index).id ?: index },
                ) { index ->
                    val product = cartItems.keys.elementAt(index)
                    val quantity = cartItems[product] ?: 0

                    ListItem(
                        headlineContent = { Text(product.name ?: "Product") },
                        supportingContent = { Text("$${product.price}") },
                        leadingContent = {
                            AsyncImage(
                                model = product.media?.firstOrNull()?.originalUrl,
                                contentDescription = null,
                                modifier = Modifier.size(56.dp),
                            )
                        },
                        trailingContent = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = { onRemoveFromCart(product, 1) }) {
                                    Icon(Icons.Default.Remove, "Decrease")
                                }
                                Text(text = quantity.toString())
                                IconButton(onClick = { onAddToCart(product, 1) }) {
                                    Icon(Icons.Default.Add, "Increase")
                                }
                            }
                        },
                    )
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                }
            }

            // Checkout button
            Button(
                onClick = onCheckout,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text("Checkout")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShoppingScreenContent() {
    AppTheme {
        ShoppingContent(
//            uiState = ShoppingUiState.Loading,
//            uiState = ShoppingUiState.Error(
//                exception = Exception("Failed to load products")
//            ),
            uiState = ShoppingUiState.Success(
                data = flowOf(
                    PagingData.from(
                        listOf(
                            Product(
                                id = 1,
                                name = "Product 1",
                                price = 100,
                                media = listOf(MediaItem(originalUrl = "https://example.com/product1.jpg")),
                            ),
                            Product(
                                id = 2,
                                name = "Product 1",
                                price = 100,
                                media = listOf(MediaItem(originalUrl = "https://example.com/product1.jpg")),
                            ),
                            Product(
                                id = 3,
                                name = "Product 1",
                                price = 100,
                                media = listOf(MediaItem(originalUrl = "https://example.com/product1.jpg")),
                            ),
                            Product(
                                id = 4,
                                name = "Product 1",
                                price = 100,
                                media = listOf(MediaItem(originalUrl = "https://example.com/product1.jpg")),
                            ),

                            ),
                    ),
                ),
            ),
            cartItems = emptyMap(),
            onProductClick = {},
            onAddToCart = { _, _ -> },
            onRemoveFromCart = { _, _ -> },
            onViewCartClick = {},
        )
    }
}
