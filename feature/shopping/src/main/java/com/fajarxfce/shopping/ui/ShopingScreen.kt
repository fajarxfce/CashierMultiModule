package com.fajarxfce.shopping.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.fajarxfce.core.designsystem.theme.AppTheme
import com.fajarxfce.core.designsystem.theme.dark_primaryContainer
import com.fajarxfce.core.designsystem.theme.dark_surface
import com.fajarxfce.core.exception.UnauthorizedException
import com.fajarxfce.core.model.data.product.Product

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ShoppingScreen(
    onAddToCart: (Int?, Int) -> Unit,
    viewModel: ShoppingViewModel = hiltViewModel(),
) {

    val shoppingUiState by viewModel.shoppingUiState.collectAsStateWithLifecycle()

    // Swipe-to-refresh implementation
    var refreshing by remember { mutableStateOf(false) }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            refreshing = true
            viewModel.refreshProducts()
        },
    )

    LaunchedEffect(shoppingUiState) {
        if (shoppingUiState is ShoppingUiState.Success || shoppingUiState is ShoppingUiState.Error) {
            refreshing = false
        }
    }

    Scaffold(
    ) { paddingValues ->
        Box(
            Modifier
                .padding(paddingValues)
                .pullRefresh(pullRefreshState),
        ) {
            when (shoppingUiState) {
                is ShoppingUiState.Loading -> {
                    if (!refreshing) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
                is ShoppingUiState.Success -> {
                    val products = (shoppingUiState as ShoppingUiState.Success).data
                    ShoppingContent(
                        products = products,
                        onAddToCart = onAddToCart,
                    )
                }
                is ShoppingUiState.Error -> {
                    val exception = (shoppingUiState as ShoppingUiState.Error).exception
                    Text(
                        text = "Error: ${exception.message}",
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }
            PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}


@Composable
fun ShoppingContent(
    modifier: Modifier = Modifier,
    products: List<Product>,
    onAddToCart: (Int?, Int) -> Unit,
) {
    val listState = rememberLazyGridState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(
            text = "Shopping",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp),
        )
        LazyVerticalGrid(
            state = listState,
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(products, key = { it.id!! }) { product ->
                ProductCard(
                    product = product,
                    onQuantityChange = { quantity ->
                        onAddToCart(product.id, quantity)
                    },
                )
            }
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

@Preview
@Composable
private fun ShoppingScreenPreview() {
    AppTheme {
        ShoppingScreen(
            onAddToCart = { _, _ -> },
        )
    }
}