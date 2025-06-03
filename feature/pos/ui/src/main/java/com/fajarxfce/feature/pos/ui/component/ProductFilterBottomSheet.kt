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
    selectedCategoryIdsInSheet: List<Int>?,
    selectedMerkIdsInSheet: List<Int>?,
    categoriesPagingItems: LazyPagingItems<Category>,
    merksPagingItems: LazyPagingItems<Merk>,
    isCategoryLoading: Boolean,
    isMerkLoading: Boolean,
    onToggleCategory: (categoryId: Int) -> Unit,
    onToggleMerk: (merkId: Int) -> Unit,
    onApplyFilters: () -> Unit,
    onResetFilters: () -> Unit
) {
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
                    fontSize = 22.sp
                ),
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .align(Alignment.CenterHorizontally),
            )

            Column(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .verticalScroll(rememberScrollState())
            ) {
                FilterChipSection(
                    title = "Kategori",
                    pagingItems = categoriesPagingItems,
                    selectedIds = selectedCategoryIdsInSheet?.toSet() ?: emptySet(),
                    isLoading = isCategoryLoading,
                    onChipSelected = { id, _ ->
                        onToggleCategory(id)
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
                    selectedIds = selectedMerkIdsInSheet?.toSet() ?: emptySet(),
                    isLoading = isMerkLoading,
                    onChipSelected = { id, _ ->
                        onToggleMerk(id)
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
                        onResetFilters()
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = CashierRed),
                    border = BorderStroke(1.dp, CashierRed.copy(alpha = 0.7f)),
                ) {
                    Text("Reset Filter", fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = {
                        onApplyFilters()
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
                    modifier = Modifier.fillMaxWidth().height(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CashierLoadingIndicator()
                }
            }

            loadState.refresh is LoadState.Error && pagingItems.itemCount == 0 -> {

            }
            loadState.refresh is LoadState.NotLoading && pagingItems.itemCount == 0 && !isLoading && loadState.append.endOfPaginationReached -> {

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
                                    selected = isSelected,
                                    enabled = true
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                    }

                    if (loadState.append is LoadState.Loading) {

                    } else if (loadState.append is LoadState.Error) {

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
        )
        val dummyMerks = listOf(
            Merk(10, "BrandX", "", 1),
        )

        val categoriesFlow = flowOf(PagingData.from(dummyCategories))
        val merksFlow = flowOf(PagingData.from(dummyMerks))

        val categoriesPagingItems = categoriesFlow.collectAsLazyPagingItems()
        val merksPagingItems = merksFlow.collectAsLazyPagingItems()

        val previewSelectedCategories = listOf(1)
        val previewSelectedMerks = emptyList<Int>()


        ProductFilterBottomSheet(
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            onDismiss = {},
            selectedCategoryIdsInSheet = previewSelectedCategories,
            selectedMerkIdsInSheet = previewSelectedMerks,
            categoriesPagingItems = categoriesPagingItems,
            merksPagingItems = merksPagingItems,
            isCategoryLoading = false,
            isMerkLoading = false,
            onToggleCategory = { categoryId ->

                println("Preview: Toggle Category $categoryId")
            },
            onToggleMerk = { merkId ->
                println("Preview: Toggle Merk $merkId")
            },
            onApplyFilters = {
                println("Preview: Apply Filters")
            },
            onResetFilters = {

                println("Preview: Reset Filters")
            }
        )
    }
}