package com.fajarxfce.feature.pos.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.fajarxfce.core.ui.component.CashierLoadingIndicator
import com.fajarxfce.core.ui.component.CashierTopAppBar
import com.fajarxfce.core.ui.component.EmptyStateView
import com.fajarxfce.core.ui.component.ErrorItemView
import com.fajarxfce.core.ui.component.ErrorStateView
import com.fajarxfce.core.ui.component.textfield.CashierSearchTextField
import com.fajarxfce.core.ui.extension.collectWithLifecycle
import com.fajarxfce.core.ui.theme.CashierBlue
import com.fajarxfce.feature.pos.domain.model.Category
import com.fajarxfce.feature.pos.domain.model.Merk
import com.fajarxfce.feature.pos.domain.model.Product
import com.fajarxfce.feature.pos.ui.component.CustomProductDetailBottomSheet
import com.fajarxfce.feature.pos.ui.component.PosBottomBar
import com.fajarxfce.feature.pos.ui.component.ProductFilterBottomSheet
import com.fajarxfce.feature.pos.ui.component.ProductItemCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PosScreen(
    modifier: Modifier = Modifier,
    uiState: PosContract.UiState,
    onAction: (PosContract.UiAction) -> Unit,
    uiEffect: Flow<PosContract.UiEffect>,
    onNavigateBack: () -> Unit,
    onNavigateToCart: () -> Unit,
) {
    val pagingItems: LazyPagingItems<Product> = uiState.productsFlow.collectAsLazyPagingItems()

    val categoryFilterPagingItems: LazyPagingItems<Category> = uiState.categoryFlow.collectAsLazyPagingItems()
    val merkFilterPagingItems: LazyPagingItems<Merk> = uiState.productMerkFlow.collectAsLazyPagingItems()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val showBottomBar by remember { mutableStateOf(true) }

    var searchQuery by uiState.searchQuery
//    var searchQuery by rememberSaveable { mutableStateOf("") }
    val selectedCategoryIds by rememberSaveable { mutableStateOf<List<Int>?>(null) }
    val selectedCategoryIdsx by rememberSaveable { mutableStateOf<List<Int>?>(null) }
    val selectedSubCategoryIds by rememberSaveable { mutableStateOf<List<Int>?>(null) }
    val selectedMerkIds by rememberSaveable { mutableStateOf<List<Int>?>(null) }

    val filterModalSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showFilterBottomSheet by rememberSaveable { mutableStateOf(false) }

    val currentParams = uiState.params
    val newParams = currentParams.copy(
        search = searchQuery,
        productCategoryId = selectedCategoryIds,
        productSubCategoryId = selectedSubCategoryIds,
        productMerkId = selectedMerkIds,
    )


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
            CashierTopAppBar(
                showNavigationIcon = true,
                onNavigationIconClick = onNavigateBack,
                titleContent = {
                    CashierSearchTextField(
                        value = searchQuery,
                        onValueChange = { query -> searchQuery = query },
                        onImeAction = {
                            val params = uiState.params.copy(
                                search = searchQuery.ifBlank { null },
                                page = 1,
                            )
                            onAction(PosContract.UiAction.LoadProducts(params))
                        },
                        placeholderText = "Search products...",
                        modifier = Modifier.height(50.dp),
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (categoryFilterPagingItems.itemCount == 0 && !uiState.isCategoryFilterLoading) {
                                onAction(PosContract.UiAction.LoadCategory(uiState.categoryParams))
                            }
                            if (merkFilterPagingItems.itemCount == 0 && !uiState.isMerkFilterLoading) {
                                onAction(PosContract.UiAction.LoadProductMerk(uiState.productMerkParams))
                            }
                            showFilterBottomSheet = true
                        },
                    ) {
                        Icon(
                            tint = CashierBlue,
                            imageVector = Icons.Filled.FilterList,
                            contentDescription = "Filter Products",
                        )
                    }
                },
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

    if (showFilterBottomSheet) {
        ProductFilterBottomSheet(
            sheetState = filterModalSheetState,
            onDismiss = { showFilterBottomSheet = false },
            onApplyFilters = { selectedCategoryIds, selectedMerkIds ->
                onAction(PosContract.UiAction.ApplyFilters(selectedCategoryIds, selectedMerkIds))
                // showFilterBottomSheet = false // Ditutup dari dalam BottomSheet
            },
            onResetFilters = {
                onAction(PosContract.UiAction.ResetAppliedFilters)
                // Pilihan reset di dalam bottom sheet sudah membersihkan temp state.
                // Jika ingin menutup sheet setelah reset, bisa ditambahkan di sini atau di dalam onResetFilters di BottomSheet.
            },
            // Berikan filter yang *sedang aktif* dari uiState.params sebagai initial
            initialSelectedCategoryIds = uiState.params.productCategoryId,
            initialSelectedMerkIds = uiState.params.productMerkId,
            categoriesPagingItems = categoryFilterPagingItems,
            merksPagingItems = merkFilterPagingItems,
            isCategoryLoading = uiState.isCategoryFilterLoading, // dari UiState
            isMerkLoading = uiState.isMerkFilterLoading        // dari UiState
        )
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
            CashierLoadingIndicator(Modifier.align(Alignment.Center))
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
                        CashierLoadingIndicator(
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