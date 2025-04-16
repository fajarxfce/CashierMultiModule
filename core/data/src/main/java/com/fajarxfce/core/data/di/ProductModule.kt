package com.fajarxfce.core.data.di

import com.fajarxfce.core.data.domain.usecase.auth.AuthInteractor
import com.fajarxfce.core.data.domain.usecase.auth.AuthUseCase
import com.fajarxfce.core.data.domain.usecase.product.GetProductInteractor
import com.fajarxfce.core.data.domain.usecase.product.GetProductUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class ProductModule {
    @Binds
    @ViewModelScoped
    abstract fun bindGetProductUseCase(productInteractor: GetProductInteractor): GetProductUseCase
}