package com.fajarxfce.feature.login.data.di

import com.fajarxfce.feature.login.data.source.LoginApi
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
    fun provideLoginApi(retrofit: Retrofit) : LoginApi =
        retrofit.create(LoginApi::class.java)
}