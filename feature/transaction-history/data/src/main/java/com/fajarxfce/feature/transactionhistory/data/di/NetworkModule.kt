package com.fajarxfce.feature.transactionhistory.data.di

import com.fajarxfce.feature.transactionhistory.data.source.TransactionHistoryApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Provides
    @Singleton
    fun provideTransactionHistoryApi(retrofit: Retrofit) =
        retrofit.create(TransactionHistoryApi::class.java)
}