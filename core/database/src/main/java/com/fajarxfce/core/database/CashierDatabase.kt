package com.fajarxfce.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fajarxfce.core.database.dao.CartDao
import com.fajarxfce.core.model.entity.CartEntity

@Database(
    entities = [
        CartEntity::class
               ],
    version = 1,
    exportSchema = true
)
internal abstract class CashierDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}