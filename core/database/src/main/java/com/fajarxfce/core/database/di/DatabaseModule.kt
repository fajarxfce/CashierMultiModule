package com.fajarxfce.core.database.di

import android.content.Context
import androidx.room.Room
import com.fajarxfce.core.database.CashierDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesCashierDatabase(
        @ApplicationContext context: Context,
    ): CashierDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = CashierDatabase::class.java,
            name = "cashier-database"
        ).build()
    }
}