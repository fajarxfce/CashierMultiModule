package com.fajarxfce.feature.pos.data.di

import com.fajarxfce.feature.pos.data.source.PosApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule{
    @Provides
    @Singleton
    fun providesPosApi(retrofit: Retrofit): PosApi = retrofit.create(PosApi::class.java)
}