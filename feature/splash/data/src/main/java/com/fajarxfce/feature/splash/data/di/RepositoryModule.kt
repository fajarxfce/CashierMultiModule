package com.fajarxfce.feature.splash.data.di

import com.fajarxfce.feature.splash.data.repository.SplashRepositoryImpl
import com.fajarxfce.feature.splash.domain.repository.SplashRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsplashRepository(repositoryImpl: SplashRepositoryImpl) : SplashRepository
}