package com.fajarxfce.feature.transactionhistory.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.fajarxfce.core.ui.delegate.mvi.MVI
import com.fajarxfce.core.ui.delegate.mvi.mvi
import com.fajarxfce.feature.transactionhistory.domain.usecase.GetAllTransactionHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class TransactionHistoryViewModel @Inject constructor(
    private val getAllTransactionHistoryUseCase: GetAllTransactionHistoryUseCase,
) : ViewModel(),
    MVI<TransactionHistoryContract.UiState, TransactionHistoryContract.UiAction, TransactionHistoryContract.UiEffect> by mvi(
        initialState = TransactionHistoryContract.UiState(),
    ) {
    override fun onAction(action: TransactionHistoryContract.UiAction) {
        when(action) {
            is TransactionHistoryContract.UiAction.LoadMoreTransactions -> {

            }
            is TransactionHistoryContract.UiAction.LoadTransactions -> {
                loadTransaction()
            }
        }
    }
    private fun loadTransaction() = viewModelScope.launch {
        val transactions = getAllTransactionHistoryUseCase().cachedIn(viewModelScope)
        updateUiState { copy(transactions = transactions) }
    }

}