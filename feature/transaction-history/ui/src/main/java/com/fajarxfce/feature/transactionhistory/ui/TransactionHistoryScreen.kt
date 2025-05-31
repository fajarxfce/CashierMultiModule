package com.fajarxfce.feature.transactionhistory.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.outlined.ShoppingCartCheckout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.fajarxfce.core.ui.component.BaseTopAppBar
import com.fajarxfce.core.ui.component.CashierText
import com.fajarxfce.core.ui.theme.AppTheme
import com.fajarxfce.core.ui.theme.CashierBlue
import com.fajarxfce.core.ui.theme.CashierGray
import com.fajarxfce.feature.transactionhistory.domain.model.TransactionHistory
import com.fajarxfce.feature.transactionhistory.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
internal fun TransactionHistoryScreen(
    uiState: TransactionHistoryContract.UiState = TransactionHistoryContract.UiState(),
    uiEffect: Flow<TransactionHistoryContract.UiEffect> = emptyFlow(),
    uiAction: (TransactionHistoryContract.UiAction) -> Unit = {},
    onNavigateBack: () -> Unit,
    onTransactionClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(key1 = true) {
        uiAction(TransactionHistoryContract.UiAction.LoadTransactions)
    }

    val pagingItems = uiState.transactions.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TransactionHistoryTopBar(onNavigateBack)
        },
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            TransactionHistoryContent(
                modifier = Modifier.fillMaxSize(),
                pagingItems = pagingItems,
                onTransactionClick = onTransactionClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TransactionHistoryTopBar(onNavigateBack: () -> Unit) {
    BaseTopAppBar(
        toolbarTitle = "Transaction History",
        backButtonAction = onNavigateBack,
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun TransactionHistoryContent(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<TransactionHistory>,
    onTransactionClick: (Int) -> Unit,
) {

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
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
                message = "No transactions found.",
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    count = pagingItems.itemCount,
                    key = pagingItems.itemKey{ it.id ?: -1 },
                ) { index ->
                    val transactions = pagingItems[index]
                    if (transactions != null){
                        TransactionCard(
                            transaction = transactions,
                            onClick = { onTransactionClick(transactions.id!!) },
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

                item {
                    if (pagingItems.loadState.append is LoadState.Loading) {
                        LoadingIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                        )
                    }
                }

            }
        }
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TransactionCard(
    transaction: TransactionHistory,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            // Header with reference number and date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Receipt,
                        contentDescription = null,
                        tint = CashierBlue,
                        modifier = Modifier.size(20.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = transaction.reference!!,
                        style = MaterialTheme.typography.titleMedium,
                        color = CashierBlue,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                Chip(
                    onClick = { },
                    colors = ChipDefaults.chipColors(
                        backgroundColor = CashierBlue.copy(alpha = 0.1f),
                    ),
                ) {
                    Text(
                        text = "Selesai",
                        color = CashierBlue,
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Date row
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Outlined.DateRange,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = formatDate(transaction.createdAt.orEmpty()),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Amount section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .padding(vertical = 12.dp, horizontal = 16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Total",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CashierGray,
                    )

                    Text(
                        text = formatCurrency(transaction.grandTotal?.toLong() ?: 0),
                        style = MaterialTheme.typography.titleMedium,
                        color = CashierBlue,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = CashierBlue)
    }
}

@Composable
private fun EmptyTransactionsView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Outlined.Receipt,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
        )

        Spacer(modifier = Modifier.height(24.dp))

        CashierText(
            text = "Belum Ada Transaksi",
            style = MaterialTheme.typography.titleLarge,
        )

        Spacer(modifier = Modifier.height(8.dp))

        CashierText(
            text = "Transaksi yang telah selesai akan muncul di sini",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

// Helper functions
private fun formatDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id"))

    return try {
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: dateString
    } catch (e: Exception) {
        dateString
    }
}

private fun formatCurrency(amount: Long): String {
    return NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(amount)
}

@Preview(showBackground = true)
@Composable
private fun TransactionHistoryScreenPreview() {
    AppTheme {
        TransactionHistoryScreen(
            uiState = TransactionHistoryContract.UiState(
                isLoading = false,
                transactions = flowOf(
                    PagingData.from(
                        listOf(
                            TransactionHistory(
                                id = 1,
                                userId = 1,
                                reference = "TRX-00001",
                                refNumber = 1,
                                total = 65000000,
                                grandTotal = 65000000,
                                createdAt = "2025-05-31T15:19:41.000000Z",
                                updatedAt = "2025-05-31T15:19:41.000000Z",
                                user = User(
                                    id = 1,
                                    name = "Admin",
                                    email = "admin@admin.com",
                                ),
                            ),
                            TransactionHistory(
                                id = 2,
                                userId = 1,
                                reference = "TRX-00002",
                                refNumber = 2,
                                total = 92400000,
                                discountFlat = 2000000,
                                discountTotal = 2000000,
                                grandTotal = 90400000,
                                createdAt = "2025-05-31T15:24:16.000000Z",
                                updatedAt = "2025-05-31T15:24:16.000000Z",
                                user = User(
                                    id = 1,
                                    name = "Admin",
                                    email = "admin@admin.com",
                                ),
                            ),
                        ),
                    ),
                ),
            ),
            onNavigateBack = {},
            onTransactionClick = {},
        )
    }
}

@Preview
@Composable
private fun EmptyTransactionsViewPreview() {
    AppTheme {
        EmptyTransactionsView()
    }
}