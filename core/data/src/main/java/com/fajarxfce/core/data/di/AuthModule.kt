package com.fajarxfce.core.data.di

import com.fajarxfce.core.data.domain.usecase.AuthInteractor
import com.fajarxfce.core.data.domain.usecase.AuthUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthModule {
    @Binds
    @ViewModelScoped
    abstract fun bindAuthUseCase(authInteractor: AuthInteractor): AuthUseCase
}