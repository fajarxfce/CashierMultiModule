package com.fajarxfce.feature.splash.data.di

import com.fajarxfce.feature.splash.data.source.SplashApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideSplashApi(retrofit: Retrofit) = retrofit.create(SplashApi::class.java)
}