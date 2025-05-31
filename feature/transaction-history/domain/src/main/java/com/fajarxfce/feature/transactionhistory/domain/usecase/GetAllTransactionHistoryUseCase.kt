package com.fajarxfce.feature.transactionhistory.domain.usecase

import androidx.paging.PagingData
import com.fajarxfce.feature.transactionhistory.domain.model.TransactionHistory
import com.fajarxfce.feature.transactionhistory.domain.repository.TransactionHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTransactionHistoryUseCase @Inject constructor(
    private val transactionHistoryRepository: TransactionHistoryRepository
) {
    suspend operator fun invoke() : Flow<PagingData<TransactionHistory>>{
        return transactionHistoryRepository.getAllTransactionHistory()
    }
}