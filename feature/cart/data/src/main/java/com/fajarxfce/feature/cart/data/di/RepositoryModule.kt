package com.fajarxfce.feature.cart.data.di

import com.fajarxfce.core.domain.repository.CartRepository
import com.fajarxfce.feature.cart.data.repository.CartRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPosRepository(cartRepositoryImpl: CartRepositoryImpl) : CartRepository
}