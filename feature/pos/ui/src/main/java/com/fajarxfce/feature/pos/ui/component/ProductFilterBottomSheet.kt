package com.fajarxfce.feature.pos.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.fajarxfce.core.ui.component.CashierLoadingIndicator
import com.fajarxfce.core.ui.theme.CashierBlue
import com.fajarxfce.core.ui.theme.CashierLightGray
import com.fajarxfce.core.ui.theme.CashierRed
import com.fajarxfce.feature.pos.domain.model.Category
import com.fajarxfce.feature.pos.domain.model.Merk
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProductFilterBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onApplyFilters: (selectedCategoryIds: List<Int>?, selectedMerkIds: List<Int>?) -> Unit,
    onResetFilters: () -> Unit,
    initialSelectedCategoryIds: List<Int>?,
    initialSelectedMerkIds: List<Int>?,
    categoriesPagingItems: LazyPagingItems<Category>,
    merksPagingItems: LazyPagingItems<Merk>,
    isCategoryLoading: Boolean,
    isMerkLoading: Boolean,
) {

    var tempSelectedCategoryIds by remember {
        mutableStateOf(initialSelectedCategoryIds?.toSet() ?: emptySet())
    }
    var tempSelectedMerkIds by remember {
        mutableStateOf(initialSelectedMerkIds?.toSet() ?: emptySet())
    }

    LaunchedEffect(initialSelectedCategoryIds) {
        tempSelectedCategoryIds = initialSelectedCategoryIds?.toSet() ?: emptySet()
    }
    LaunchedEffect(initialSelectedMerkIds) {
        tempSelectedMerkIds = initialSelectedMerkIds?.toSet() ?: emptySet()
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .width(40.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)),
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
        ) {
            Text(
                text = "Filter Produk",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp // Ukuran font yang jelas
                ),
                modifier = Modifier
                    .padding(bottom = 20.dp) // Jarak dari judul ke konten filter
                    .align(Alignment.CenterHorizontally),
            )

            // Konten filter yang bisa di-scroll jika melebihi tinggi
            Column(
                modifier = Modifier
                    .weight(1f, fill = false) // Agar bisa expand tapi tidak mengisi semua ruang jika konten sedikit
                    .verticalScroll(rememberScrollState())
            ) {
                FilterChipSection(
                    title = "Kategori",
                    pagingItems = categoriesPagingItems,
                    selectedIds = tempSelectedCategoryIds,
                    isLoading = isCategoryLoading,
                    onChipSelected = { id, isSelected ->
                        tempSelectedCategoryIds = if (isSelected) {
                            tempSelectedCategoryIds + id
                        } else {
                            tempSelectedCategoryIds - id
                        }
                    },
                    idExtractor = { it.id ?: -1 },
                    nameExtractor = { it.name ?: "N/A" },
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = CashierLightGray.copy(alpha = 0.5f)
                )

                FilterChipSection(
                    title = "Merk/Brand",
                    pagingItems = merksPagingItems,
                    selectedIds = tempSelectedMerkIds,
                    isLoading = isMerkLoading,
                    onChipSelected = { id, isSelected ->
                        tempSelectedMerkIds = if (isSelected) {
                            tempSelectedMerkIds + id
                        } else {
                            tempSelectedMerkIds - id
                        }
                    },
                    idExtractor = { it.id ?: -1 },
                    nameExtractor = { it.name ?: "N/A" },
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedButton(
                    onClick = {
                        tempSelectedCategoryIds = emptySet()
                        tempSelectedMerkIds = emptySet()
                        onResetFilters()
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp), // Bentuk tombol yang modern
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = CashierRed),
                    border = BorderStroke(
                        1.dp,
                        CashierRed.copy(alpha = 0.7f)
                    ),
                ) {
                    Text("Reset Filter", fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = {
                        onApplyFilters(
                            tempSelectedCategoryIds.toList().ifEmpty { null },
                            tempSelectedMerkIds.toList().ifEmpty { null },
                        )
                        onDismiss()
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CashierBlue),
                ) {
                    Text("Terapkan", fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun <T : Any> FilterChipSection(
    title: String,
    pagingItems: LazyPagingItems<T>,
    selectedIds: Set<Int>,
    isLoading: Boolean,
    onChipSelected: (id: Int, isSelected: Boolean) -> Unit,
    idExtractor: (T) -> Int,
    nameExtractor: (T) -> String,
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 12.dp),
        )

        val loadState = pagingItems.loadState
        when {
            (loadState.refresh is LoadState.Loading || isLoading) && pagingItems.itemCount == 0 -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CashierLoadingIndicator()
                }
            }
            loadState.refresh is LoadState.Error && pagingItems.itemCount == 0 -> {
                val error = (loadState.refresh as LoadState.Error).error
                Text(
                    text = "Gagal memuat ${title.lowercase()}.\n${error.localizedMessage ?: "Coba lagi nanti."}",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Button(onClick = { pagingItems.retry() }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text("Coba Lagi")
                }
            }
            loadState.refresh is LoadState.NotLoading && pagingItems.itemCount == 0 && !isLoading && loadState.append.endOfPaginationReached -> {
                Text(
                    text = "Tidak ada ${title.lowercase()} tersedia.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            else -> {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    for (index in 0 until pagingItems.itemCount) {
                        val item = pagingItems[index]
                        if (item != null) {
                            val itemId = idExtractor(item)
                            val itemName = nameExtractor(item)
                            val isSelected = selectedIds.contains(itemId)

                            FilterChip(
                                selected = isSelected,
                                onClick = { onChipSelected(itemId, !isSelected) },
                                label = {
                                    Text(
                                        text = itemName,
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(

                                    selectedContainerColor = CashierBlue,
                                    selectedLabelColor = Color.White,

                                    containerColor = Color.White,
                                    labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                                    selectedBorderColor = CashierBlue.copy(alpha = 0.8f),
                                    borderWidth = 1.dp,
                                    selectedBorderWidth = 1.5.dp,
                                    enabled = true,
                                    selected = isSelected
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                    }

                    if (loadState.append is LoadState.Loading) {
                        Box(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), contentAlignment = Alignment.Center) {
                            CashierLoadingIndicator()
                        }
                    } else if (loadState.append is LoadState.Error) {
                        Text(
                            "Gagal memuat lebih banyak. Coba lagi.",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = { pagingItems.retry() },
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Text("Coba Lagi (Append)")
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun ProductFilterBottomSheetPreview() {
    MaterialTheme {
        val dummyCategories = listOf(
            Category(1, "Elektronik", "", 1),
            Category(2, "Fashion Pria Super Panjang Sekali Nama Kategorinya", "", 1),
            Category(3, "Rumah Tangga", "", 1),
            Category(4, "Kecantikan", "", 1),
            Category(5, "Olahraga & Outdoor", "", 1),
        )
        val dummyMerks = listOf(
            Merk(10, "BrandX", "", 1),
            Merk(11, "BrandY", "", 1),
            Merk(12, "BrandZ Internasional", "", 1),
            Merk(13, "Merk Lokal Jaya", "", 1),
        )

        val categoriesFlow = flowOf(PagingData.from(dummyCategories))
        val merksFlow = flowOf(PagingData.from(dummyMerks))

        val categoriesPagingItems = categoriesFlow.collectAsLazyPagingItems()
        val merksPagingItems = merksFlow.collectAsLazyPagingItems()

        var initialSelectedCategories by remember { mutableStateOf<List<Int>?>(listOf(1)) }
        var initialSelectedMerks by remember { mutableStateOf<List<Int>?>(null) }

        ProductFilterBottomSheet(
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            onDismiss = {},
            onApplyFilters = { selectedCategories, selectedMerks ->
                initialSelectedCategories = selectedCategories
                initialSelectedMerks = selectedMerks
            },
            onResetFilters = {
                initialSelectedCategories = null
                initialSelectedMerks = null
            },
            initialSelectedCategoryIds = initialSelectedCategories,
            initialSelectedMerkIds = initialSelectedMerks,
            categoriesPagingItems = categoriesPagingItems,
            merksPagingItems = merksPagingItems,
            isCategoryLoading = false,
            isMerkLoading = false
        )
    }
}