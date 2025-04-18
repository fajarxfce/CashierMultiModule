package com.fajarxfce.core.data.di

import com.fajarxfce.core.data.repository.DefaultAuthRepository
import com.fajarxfce.core.data.repository.DefaultProductRepository
import com.fajarxfce.core.domain.repository.AuthRepository
import com.fajarxfce.core.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindAuthRepository(
        defaultAuthRepository: DefaultAuthRepository
    ): AuthRepository

    @Binds
    abstract fun bindGetAllProductRepository(
        defaultGetAllProductRepository: DefaultProductRepository
    ): ProductRepository
}