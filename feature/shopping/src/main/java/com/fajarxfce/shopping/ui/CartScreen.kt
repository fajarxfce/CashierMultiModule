package com.fajarxfce.shopping.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.fajarxfce.core.designsystem.component.DisplayImage
import com.fajarxfce.core.designsystem.theme.AppTheme
import com.fajarxfce.core.model.data.product.MediaItem
import com.fajarxfce.core.model.data.product.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: ShoppingViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onCheckoutClick: () -> Unit,
) {
    val cartItems by viewModel.cartItems.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    CartContent(
        modifier = modifier,
        cartItems = cartItems,
        onBackClick = onBackClick,
        onCheckoutClick = onCheckoutClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    modifier: Modifier = Modifier,
    cartItems: Map<Product, Int>,
    onBackClick: () -> Unit,
    onCheckoutClick: () -> Unit,
    onAddToCart: (Product, Int) -> Unit = { _, _ -> },
    onRemoveFromCart: (Product, Int) -> Unit = { _, _ -> }
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Your Cart",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            if (cartItems.isNotEmpty()) {
                CheckoutButton(
                    itemCount = cartItems.values.sum(),
                    totalAmount = cartItems.entries.sumOf { (product, quantity) ->
                        product.price?.toDouble()?.times(quantity) ?: 0.0
                    },
                    onClick = onCheckoutClick
                )
            }
        },
        contentWindowInsets = WindowInsets.safeDrawing,
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(paddingValues),
        ) {
            if (cartItems.isEmpty()) {
                EmptyCartMessage(onBackClick = onBackClick)
            } else {
                CartItemsList(
                    cartItems = cartItems,
                    onUpdateQuantity = { product, amount ->
                        if (amount > 0) {
                            onAddToCart(product, amount)
                        } else {
                            onRemoveFromCart(product, -amount)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun CartItemsList(
    cartItems: Map<Product, Int>,
    onUpdateQuantity: (Product, Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = cartItems.entries.toList(),
            key = { it.key.id ?: 0 }
        ) { (product, quantity) ->
            CartItem(
                product = product,
                quantity = quantity,
                onIncreaseQuantity = { onUpdateQuantity(product, 1) },
                onDecreaseQuantity = { onUpdateQuantity(product, -1) },
                onRemove = { onUpdateQuantity(product, -quantity) }
            )
        }

        // Add space at bottom for better UX
        item {
            Spacer(modifier = Modifier.height(72.dp))
        }
    }
}

@Composable
private fun CartItem(
    product: Product,
    quantity: Int,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Product image
            DisplayImage(
                imageUrl = product.media?.firstOrNull()?.originalUrl,
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name ?: "Unknown Product",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    QuantitySelector(
                        quantity = quantity,
                        onIncrease = onIncreaseQuantity,
                        onDecrease = onDecreaseQuantity
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    TextButton(
                        onClick = onRemove,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Remove item",
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Remove")
                    }
                }
            }
        }
    }
}

@Composable
private fun QuantitySelector(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onDecrease,
            enabled = quantity > 1
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrease quantity"
            )
        }

        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        IconButton(
            onClick = onIncrease
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase quantity"
            )
        }
    }
}

@Composable
private fun CheckoutButton(
    itemCount: Int,
    totalAmount: Double,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$${String.format("%.2f", totalAmount)} • Checkout",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "$itemCount ${if (itemCount == 1) "item" else "items"}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun EmptyCartMessage(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Your cart is empty",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Looks like you haven't added any products to your cart yet.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Continue Shopping")
        }
    }
}

@Preview
@Composable
private fun CartContentPreview() {
    AppTheme {
        CartContent(
            cartItems = mapOf(
                Product(
                    id = 1,
                    name = "Sample Product",
                    price = 1999,
                    media = listOf(MediaItem(originalUrl = "https://example.com/image.jpg"))
                ) to 2
            ),
            onBackClick = {},
            onCheckoutClick = {}
        )
    }
}