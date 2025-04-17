package com.fajarxfce.core.data.di

import com.fajarxfce.core.data.repository.DefaultAuthRepository
import com.fajarxfce.core.data.repository.DefaultGetAllProductRepository
import com.fajarxfce.core.domain.repository.AuthRepository
import com.fajarxfce.core.domain.repository.GetAllProductRepository
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
        defaultGetAllProductRepository: DefaultGetAllProductRepository
    ): GetAllProductRepository
}