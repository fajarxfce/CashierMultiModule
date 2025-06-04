package com.fajarxfce.feature.transactionhistory.data

import com.fajarxfce.core.base.BasePagingSource
import com.fajarxfce.feature.transactionhistory.data.mapper.toDomainList
import com.fajarxfce.feature.transactionhistory.data.model.TransactionDataItem
import com.fajarxfce.feature.transactionhistory.data.source.TransactionHistoryApi
import com.fajarxfce.feature.transactionhistory.domain.model.TransactionHistory
import javax.inject.Inject

class TransactionHistoryPagingSource @Inject constructor(
    private val api: TransactionHistoryApi
) : BasePagingSource<TransactionHistory, TransactionDataItem>() {
    override suspend fun fetchData(
        page: Int,
        pageSize: Int,
    ): List<TransactionDataItem> {
        val response = api.getTransactions()
        return response
            ?.data
            ?.data
            ?.filterNotNull()
            ?: emptyList()
    }

    override fun mapToLocalData(remoteData: List<TransactionDataItem>): List<TransactionHistory> {
        return remoteData.toDomainList()
    }

}