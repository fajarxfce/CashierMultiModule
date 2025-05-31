package com.fajarxfce.feature.transactionhistory.domain.repository

import androidx.paging.PagingData
import com.fajarxfce.feature.transactionhistory.domain.model.TransactionHistory
import kotlinx.coroutines.flow.Flow

interface TransactionHistoryRepository {
    suspend fun getAllTransactionHistory(): Flow<PagingData<TransactionHistory>>
}