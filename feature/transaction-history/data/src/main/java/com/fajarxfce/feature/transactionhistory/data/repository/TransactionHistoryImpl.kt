package com.fajarxfce.feature.transactionhistory.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fajarxfce.core.base.DEFAULT_PAGE_SIZE
import com.fajarxfce.feature.transactionhistory.data.TransactionHistoryPagingSource
import com.fajarxfce.feature.transactionhistory.data.source.TransactionHistoryApi
import com.fajarxfce.feature.transactionhistory.domain.model.TransactionHistory
import com.fajarxfce.feature.transactionhistory.domain.repository.TransactionHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionHistoryImpl @Inject constructor(
    private val transactionHistoryApi: TransactionHistoryApi,
) : TransactionHistoryRepository {
    override suspend fun getAllTransactionHistory(): Flow<PagingData<TransactionHistory>> {
        return Pager(
            pagingSourceFactory = { TransactionHistoryPagingSource(transactionHistoryApi) },
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                prefetchDistance = DEFAULT_PAGE_SIZE / 2,
                enablePlaceholders = false,
            ),
        ).flow
    }

}