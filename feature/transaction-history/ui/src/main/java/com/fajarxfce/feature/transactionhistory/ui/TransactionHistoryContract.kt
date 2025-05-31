package com.fajarxfce.feature.transactionhistory.ui

import androidx.paging.PagingData
import com.fajarxfce.feature.transactionhistory.domain.model.TransactionHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

object TransactionHistoryContract {
    data class UiState(
        val isLoading: Boolean = false,
        val transactions: Flow<PagingData<TransactionHistory>> = emptyFlow(),
        val error: String? = null,
        val currentPage: Int = 1,
        val lastPage: Int = 1
    )

    sealed interface UiAction {
        data object LoadTransactions : UiAction
        data class LoadMoreTransactions(val page: Int) : UiAction
    }

    sealed interface UiEffect {
        data class ShowSnackbar(val message: String, val isError: Boolean = false) : UiEffect

    }
}