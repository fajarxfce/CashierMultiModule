package com.fajarxfce.core.data.di

import com.fajarxfce.core.data.AuthRepository
import com.fajarxfce.core.data.domain.repository.IAuthRepository
import com.fajarxfce.core.data.source.remote.auth.AuthDataSource
import com.fajarxfce.core.data.source.remote.auth.AuthDataSourceImpl
import com.fajarxfce.core.data.source.remote.product.GetProductDataSource
import com.fajarxfce.core.data.source.remote.product.GetProductDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthDataSource(
        authDataSourceImpl: AuthDataSourceImpl,
    ): AuthDataSource

    @Binds
    abstract fun provideAuthRepository(authRepository: AuthRepository): IAuthRepository

    @Binds
    abstract fun bindGetProductDataSource(
        getProductDataSourceImpl: GetProductDataSourceImpl,
    ): GetProductDataSource

    @Binds
    abstract fun provideGetProductRepository(getProductRepository: GetProductDataSourceImpl): GetProductDataSource
}