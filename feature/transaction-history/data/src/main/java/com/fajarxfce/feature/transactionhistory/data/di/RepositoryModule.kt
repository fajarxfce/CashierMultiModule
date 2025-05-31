package com.fajarxfce.feature.transactionhistory.data.di

import com.fajarxfce.feature.transactionhistory.data.repository.TransactionHistoryImpl
import com.fajarxfce.feature.transactionhistory.domain.repository.TransactionHistoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindTransactionHistoryRepository(transactionHistoryRepositoryImpl: TransactionHistoryImpl): TransactionHistoryRepository
}