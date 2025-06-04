package com.fajarxfce.feature.cart.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fajarxfce.feature.cart.data.source.local.dao.CartDao
import com.fajarxfce.feature.cart.data.source.local.entity.CartItemEntity

@Database(
    entities = [
        CartItemEntity::class
    ],
    version = 1,
    exportSchema = true
)
internal abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}