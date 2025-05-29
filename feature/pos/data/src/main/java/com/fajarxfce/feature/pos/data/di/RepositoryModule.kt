package com.fajarxfce.feature.pos.data.di

import com.fajarxfce.feature.pos.data.repository.CartRepositoryImpl
import com.fajarxfce.feature.pos.data.repository.PosRepositoryImpl
import com.fajarxfce.feature.pos.domain.repository.CartRepository
import com.fajarxfce.feature.pos.domain.repository.PosRepository
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
    abstract fun bindPosRepository(
        posRepositoryImpl: PosRepositoryImpl
    ): PosRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(
        cartRepositoryImpl: CartRepositoryImpl
    ): CartRepository

}