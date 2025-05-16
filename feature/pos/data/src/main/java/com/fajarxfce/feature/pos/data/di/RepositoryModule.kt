package com.fajarxfce.feature.pos.data.di

import com.fajarxfce.feature.pos.data.repository.PosRepositoryImpl
import com.fajarxfce.feature.pos.domain.repository.PosRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Binds
    abstract fun bindPosRepository(posRepositoryImpl: PosRepositoryImpl) : PosRepository
}