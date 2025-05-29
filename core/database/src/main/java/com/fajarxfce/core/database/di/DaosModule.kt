package com.fajarxfce.core.database.di

import com.fajarxfce.core.database.CashierDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    @Singleton
    fun providesCartDao(database: CashierDatabase) = database.cartDao()
}