package com.fajarxfce.core.data.di

import com.fajarxfce.core.data.AuthRepository
import com.fajarxfce.core.data.domain.repository.IAuthRepository
import com.fajarxfce.core.data.source.remote.AuthDataSource
import com.fajarxfce.core.data.source.remote.AuthDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthDataSource(
        authDataSourceImpl: AuthDataSourceImpl
    ): AuthDataSource

    @Binds
    abstract fun provideAuthRepository(authRepository: AuthRepository) : IAuthRepository
}