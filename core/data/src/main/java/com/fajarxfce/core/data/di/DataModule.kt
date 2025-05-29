package com.fajarxfce.core.data.di

import com.fajarxfce.core.domain.repository.AuthRepository
import com.fajarxfce.core.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
}