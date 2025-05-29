package com.fajarxfce.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fajarxfce.core.database.entity.CartEntity

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartEntity: CartEntity)
    @Query("UPDATE carts SET quantity = :quantity + quantity WHERE productId = :productId")
    suspend fun  increaseQuantity(productId: Int, quantity: Int)
}