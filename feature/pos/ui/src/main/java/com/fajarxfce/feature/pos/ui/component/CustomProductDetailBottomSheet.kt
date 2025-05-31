package com.fajarxfce.feature.pos.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.fajarxfce.core.ui.theme.CashierBlue // Asumsi Anda punya warna ini
import com.fajarxfce.core.ui.theme.CashierGray
import com.fajarxfce.feature.pos.domain.model.Product


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomProductDetailBottomSheet(
    product: Product,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onAddToCart: (product: Product?, quantity: Int) -> Unit,
) {
    var quantity by rememberSaveable(product?.id) { mutableIntStateOf(1) } // Reset quantity saat produk berubah

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp), // Bentuk kustom
        containerColor = Color.White, // Warna latar kustom
        dragHandle = { // Drag handle kustom atau hilangkan jika tidak mau
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .background(
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                            CircleShape,
                        ),
                )
            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp), // Padding untuk tombol CTA
        ) {
            // Tombol Close di pojok kanan atas (opsional)
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp),
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            // Gambar Produk dengan efek gradasi
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp) // Sesuaikan tinggi gambar
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(16.dp)),
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(product?.imageUrl.orEmpty())
                            .crossfade(true)
                            // .placeholder(R.drawable.placeholder_image) // Ganti dengan placeholder Anda
                            // .error(R.drawable.error_image) // Ganti dengan error image Anda
                            .build(),
                    ),
                    contentDescription = product?.name ?: "Product Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
                // Gradasi di atas gambar untuk kontras teks jika ada teks di atas gambar
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.5f)),
                                startY = 100f,
                            ),
                        ),
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Detail Teks
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp),
            ) {
                Text(
                    text = product?.name.orEmpty(),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface, // Warna teks utama
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Rp ${product?.price?.toString() ?: "0"}", // Format harga
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = CashierBlue, // Warna harga yang menonjol
                    ),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = product?.description.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant, // Warna teks sekunder
                    ),
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Spacer(modifier = Modifier.weight(1f)) // Dorong konten berikutnya ke bawah

            // Counter dan Tombol Add to Cart
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                QuantityCounter(
                    quantity = quantity,
                    onQuantityChange = { newQuantity ->
                        if (newQuantity >= 1) quantity = newQuantity
                    },
                )

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { onAddToCart(product, quantity) },
                    modifier = Modifier
                        .weight(1f) // Agar tombol memenuhi sisa ruang
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp), // Bentuk tombol kustom
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CashierBlue, // Warna tombol utama
                        contentColor = Color.White,
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Add to Cart", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
fun QuantityCounter(
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp)) // Bentuk kustom untuk counter
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)) // Latar belakang counter
            .padding(horizontal = 4.dp, vertical = 4.dp),
    ) {
        CounterButton(icon = Icons.Filled.Remove, enabled = quantity > 1) {
            onQuantityChange(quantity - 1)
        }

        Text(
            text = "$quantity",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp),
            color = CashierGray,
        )

        CounterButton(icon = Icons.Filled.Add) {
            onQuantityChange(quantity + 1)
        }
    }
}

@Composable
private fun CounterButton(
    icon: ImageVector,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .size(36.dp) // Ukuran tombol counter
            .background(
                if (enabled) CashierBlue.copy(alpha = 0.1f) else Color.Transparent, // Latar tombol
                CircleShape,
            ),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (enabled) CashierBlue else MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CustomProductDetailBottomSheetPreview() {
    val product = Product(
        id = 1,
        name = "Product Name",
        description = "Product Description",
        price = 100,
        imageUrl = "https://example.com/image.jpg"
    )
    val sheetState = rememberModalBottomSheetState()
    CustomProductDetailBottomSheet(
        product = product,
        sheetState = sheetState,
        onDismiss = {},
        onAddToCart = { _, _ -> }
    )
}

@Preview
@Composable
fun QuantityCounterPreview() {
    QuantityCounter(quantity = 1, onQuantityChange = {})
}

@Preview
@Composable
fun CounterButtonPreview() {
    CounterButton(icon = Icons.Filled.Add, onClick = {})
}