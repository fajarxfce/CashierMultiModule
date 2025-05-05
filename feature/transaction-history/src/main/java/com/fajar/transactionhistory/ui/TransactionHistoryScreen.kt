package com.fajar.transactionhistory.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajarxfce.feature.transactionhistory.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionHistoryScreen(modifier: Modifier = Modifier) {
    val transactions = remember { generateSampleTransactions() }
    var filteredTransactions by remember { mutableStateOf(transactions) }
    var filterExpanded by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.transaction_history),
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                    ),
                    actions = {
                        IconButton(onClick = { /* Open search */ }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search transactions",
                            )
                        }
                        IconButton(onClick = { filterExpanded = !filterExpanded }) {
                            Icon(
                                imageVector = Icons.Default.FilterList,
                                contentDescription = "Filter transactions",
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,
                )
                TransactionSummary()
            }
        },
        contentWindowInsets = WindowInsets.safeDrawing,
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = paddingValues,
        ) {
            // Group transactions by date
            val groupedTransactions = transactions.groupBy { it.formattedDate }

            groupedTransactions.forEach { (date, transactionsForDate) ->
                item {
                    DateHeader(date)
                }

                items(transactionsForDate) { transaction ->
                    TransactionItem(transaction)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun TransactionSummary() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "This Month",
                style = MaterialTheme.typography.labelLarge,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF4CAF50).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowDownward,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Color(0xFF4CAF50),
                            )
                        }
                        Text(
                            text = "Income",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 8.dp),
                        )
                    }
                    Text(
                        text = "$1,256.00",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF44336).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowUpward,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Color(0xFFF44336),
                            )
                        }
                        Text(
                            text = "Expense",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 8.dp),
                        )
                    }
                    Text(
                        text = "$876.50",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

@Composable
fun DateHeader(date: String) {
    Text(
        text = date,
        style = MaterialTheme.typography.labelLarge.copy(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium,
        ),
        modifier = Modifier.padding(vertical = 12.dp),
    )
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Transaction icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(transaction.categoryColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = transaction.categoryIcon,
                    contentDescription = null,
                    tint = transaction.categoryColor,
                    modifier = Modifier.size(24.dp),
                )
            }

            // Transaction details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp),
            ) {
                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                    ),
                )
                Text(
                    text = transaction.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = transaction.time,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                )
            }

            // Amount
            Text(
                text = transaction.amount,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (transaction.isExpense) Color(0xFFF44336) else Color(0xFF4CAF50),
                ),
            )
        }
    }
}

// Data model for transaction
data class Transaction(
    val id: String,
    val title: String,
    val description: String,
    val amount: String,
    val isExpense: Boolean,
    val date: Date,
    val time: String,
    val categoryIcon: ImageVector,
    val categoryColor: Color,
) {
    val formattedDate: String
        get() {
            val today = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
            val transactionDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(date)

            return when (transactionDate) {
                today -> "Today"
                else -> SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(date)
            }
        }
}

// Sample data
private fun generateSampleTransactions(): List<Transaction> {
    return listOf(
        Transaction(
            id = "1",
            title = "Grocery Shopping",
            description = "Supermarket",
            amount = "-$56.30",
            isExpense = true,
            date = Date(),
            time = "10:30 AM",
            categoryIcon = Icons.Default.ShoppingBag,
            categoryColor = Color(0xFFF44336),
        ),
        Transaction(
            id = "2",
            title = "Salary Deposit",
            description = "Monthly salary",
            amount = "+$2,450.00",
            isExpense = false,
            date = Date(),
            time = "9:15 AM",
            categoryIcon = Icons.Default.ArrowDownward,
            categoryColor = Color(0xFF4CAF50),
        ),
        Transaction(
            id = "3",
            title = "Online Purchase",
            description = "Electronics Store",
            amount = "-$129.99",
            isExpense = true,
            date = Date(System.currentTimeMillis() - 86400000), // yesterday
            time = "3:45 PM",
            categoryIcon = Icons.Default.ShoppingBag,
            categoryColor = Color(0xFFF44336),
        ),
        Transaction(
            id = "4",
            title = "Refund",
            description = "Return of damaged item",
            amount = "+$24.50",
            isExpense = false,
            date = Date(System.currentTimeMillis() - 86400000), // yesterday
            time = "11:20 AM",
            categoryIcon = Icons.Default.ArrowDownward,
            categoryColor = Color(0xFF4CAF50),
        ),
        Transaction(
            id = "5",
            title = "Restaurant",
            description = "Dinner with friends",
            amount = "-$78.25",
            isExpense = true,
            date = Date(System.currentTimeMillis() - 172800000), // 2 days ago
            time = "7:30 PM",
            categoryIcon = Icons.Default.ShoppingBag,
            categoryColor = Color(0xFFF44336),
        ),
        Transaction(
            id = "6",
            title = "Restaurant",
            description = "Dinner with friends",
            amount = "-$78.25",
            isExpense = true,
            date = Date(System.currentTimeMillis() - 172800000), // 2 days ago
            time = "7:30 PM",
            categoryIcon = Icons.Default.ShoppingBag,
            categoryColor = Color(0xFFF44336),
        ),
        Transaction(
            id = "7",
            title = "Restaurant",
            description = "Dinner with friends",
            amount = "-$78.25",
            isExpense = true,
            date = Date(System.currentTimeMillis() - 102800000), // 2 days ago
            time = "7:30 PM",
            categoryIcon = Icons.Default.ShoppingBag,
            categoryColor = Color(0xFFF44336),
        ),
        Transaction(
            id = "8",
            title = "Restaurant",
            description = "Dinner with friends",
            amount = "-$78.25",
            isExpense = true,
            date = Date(System.currentTimeMillis() - 92800000), // 2 days ago
            time = "7:30 PM",
            categoryIcon = Icons.Default.ShoppingBag,
            categoryColor = Color(0xFFF44336),
        ),
    )
}

@Preview
@Composable
private fun TransactionHistoryScreenPreview() {
    TransactionHistoryScreen()
}