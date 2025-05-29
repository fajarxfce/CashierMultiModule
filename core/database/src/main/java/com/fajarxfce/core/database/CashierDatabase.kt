package com.fajarxfce.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fajarxfce.core.database.dao.CartDao

@Database(
    entities = [],
    version = 1,
    exportSchema = true
)
internal abstract class CashierDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}