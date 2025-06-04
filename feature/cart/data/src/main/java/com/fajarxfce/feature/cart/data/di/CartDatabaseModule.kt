package com.fajarxfce.feature.cart.data.di

import android.content.Context
import androidx.room.Room
import com.fajarxfce.feature.cart.data.source.local.dao.CartDao
import com.fajarxfce.feature.cart.data.source.local.CartDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object CartDatabaseModule {
    @Provides
    @Singleton
    fun provideCartDatabase(
        @ApplicationContext context: Context
    ): CartDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            CartDatabase::class.java,
            "cart_db",
            ).build()
    }

    @Provides
    @Singleton
    fun provideCartDao(cartDatabase: CartDatabase): CartDao {
        return cartDatabase.cartDao()
    }
}