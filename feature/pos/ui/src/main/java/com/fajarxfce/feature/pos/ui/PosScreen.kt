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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.ShoppingCartCheckout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.fajarxfce.core.ui.component.CashierText
import com.fajarxfce.core.ui.component.textfield.CashierSearchTextField
import com.fajarxfce.core.ui.extension.collectWithLifecycle
import com.fajarxfce.core.ui.theme.CashierBlue
import com.fajarxfce.feature.pos.domain.model.Product
import com.fajarxfce.feature.pos.domain.params.GetAllProductParams
import com.fajarxfce.feature.pos.ui.component.CashierFilterChip
import com.fajarxfce.feature.pos.ui.component.CustomProductDetailBottomSheet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PosScreen(
    uiState: PosContract.UiState,
    onAction: (PosContract.UiAction) -> Unit,
    uiEffect: Flow<PosContract.UiEffect>,
    onNavigateBack: () -> Unit,
    onNavigateToCart: () -> Unit,
) {
    val pagingItems: LazyPagingItems<Product> = uiState.productsFlow.collectAsLazyPagingItems()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val showBottomBar by remember { mutableStateOf(true) }

    val searchQuery by rememberSaveable { mutableStateOf("") }
    val selectedCategoryIds by rememberSaveable { mutableStateOf<List<Int>?>(null) }
    val selectedSubCategoryIds by rememberSaveable { mutableStateOf<List<Int>?>(null) }
    val selectedMerkIds by rememberSaveable { mutableStateOf<List<Int>?>(null) }

    val currentParams = uiState.params
    val newParams = currentParams.copy(
        search = searchQuery,
        productCategoryId = selectedCategoryIds,
        productSubCategoryId = selectedSubCategoryIds,
        productMerkId = selectedMerkIds,
    )


    LaunchedEffect(key1 = true) {
        onAction(PosContract.UiAction.LoadProducts(newParams))
        onAction(PosContract.UiAction.LoadTotalCartItem)
    }

    uiEffect.collectWithLifecycle { effect ->
        when (effect) {
            is PosContract.UiEffect.ShowProductDetailsSheet -> {
//                if (uiState.productForSheet != null){
                showBottomSheet = true
//                }
            }

            is PosContract.UiEffect.HideProductDetailsSheet -> {
                scope.launch {
                    modalSheetState.hide()
                }.invokeOnCompletion {
                    if (!modalSheetState.isVisible) {
                        showBottomSheet = false
                        onAction(PosContract.UiAction.OnDismissProductDetailsSheet)
                    }
                }
            }

            is PosContract.UiEffect.ShowSnackbar -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        withDismissAction = true,
                        duration = if (effect.isError) SnackbarDuration.Long else SnackbarDuration.Short,
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            BaseTopAppBar(
                toolbarTitle = "Select Product",
                backButtonAction = onNavigateBack,
            )
        },
        containerColor = Color.White,
        bottomBar = {
            PosBottomBar(
                modifier = Modifier.fillMaxWidth(),
                totalCartItem = uiState.totalCartItem,
                onNavigateToCart = onNavigateToCart,
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
            ) {
                CashierSearchTextField(
                    text = searchQuery,
                    onTextChange = { query ->
//                        searchQuery = query
                    },
                    onImeAction = {
                        val newParams = uiState.params.copy(
                            search = if (searchQuery.isNotBlank()) searchQuery else null,
                            page = 1,
                        )
                        onAction(PosContract.UiAction.LoadProducts(newParams))
                    },
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp,
                    ),
                    showFilterIcon = true,
                    onFilterClick = {}
                )

                PosContent(
                    modifier = Modifier.weight(1f),
                    pagingItems = pagingItems,
                    onProductClick = { product ->
                        onAction(PosContract.UiAction.OnProductItemClick(product))
                    },
                )
            }
        },
    )

    val productToShowInSheet = uiState.productForSheet

    if (showBottomSheet && productToShowInSheet != null) {
        CustomProductDetailBottomSheet(
            product = productToShowInSheet,
            sheetState = modalSheetState,
            onDismiss = {
                showBottomSheet = false
                onAction(PosContract.UiAction.OnDismissProductDetailsSheet)
            },
            onAddToCart = { product, quantity ->
                product?.let { onAction(PosContract.UiAction.AddToCartFromDetail(it, quantity)) }
                showBottomSheet = false
            },
        )
    }
}
@Composable
private fun PosBottomBar(
    modifier: Modifier,
    totalCartItem: Int = 0,
    onNavigateToCart: () -> Unit,
) {
    Surface(
        modifier = modifier,
        shadowElevation = 8.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                CashierText(
                    text = "Total ${totalCartItem} item",
                    style = MaterialTheme.typography.bodyMedium,
                )

                Text(
                    text = "Rp 500,000",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = CashierBlue,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onNavigateToCart,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CashierBlue,
                ),
            ) {
                Text(
                    text = "Go To Cart",
                    modifier = Modifier.padding(vertical = 4.dp),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

@Composable
private fun PosContent(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<Product>,
    onProductClick: (Product) -> Unit,
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
                            onClick = { product.id?.let { onProductClick(product) } },
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
    val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
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
                CashierText(
                    text = product.name.orEmpty(),
                    maxLines = 2,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                )
                Spacer(modifier = Modifier.height(8.dp))
                CashierText(
                    text = formatter.format(product.price), // Format harga sesuai kebutuhan
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
                productForSheet = Product(
                    id = 1,
                    name = "aasas",
                    description = "asasas",
                    price = 234234,
                    imageUrl = "",
                ),
            ),
            onAction = {},
            uiEffect = emptyFlow(),
            onNavigateBack = {},
            onNavigateToCart = {},
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
                isLoading = false,
                productForSheet = Product(
                    id = 1,
                    name = "aasas",
                    description = "asasas",
                    price = 234234,
                    imageUrl = "",
                ),
            ),
            onAction = {},
            uiEffect = emptyFlow<PosContract.UiEffect>(),
            onNavigateBack = {},
            onNavigateToCart = {},
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