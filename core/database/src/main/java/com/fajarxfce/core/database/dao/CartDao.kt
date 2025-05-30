package com.fajarxfce.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.fajarxfce.core.model.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM carts where productId = :productId")
    fun getItem(productId: Int): CartEntity?

    @Query("SELECT * FROM carts")
    fun getAll(): Flow<List<CartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartEntity: CartEntity)

    @Query("UPDATE carts SET quantity = :quantity + quantity WHERE productId = :productId")
    suspend fun  increaseQuantity(productId: Int, quantity: Int)

    @Query("SELECT * FROM carts WHERE productId = :productId")
    fun getProductById(productId: Int): CartEntity?

    @Query("UPDATE carts SET quantity = quantity + :newQuantity WHERE productId = :productId")
    suspend fun updateQuantity(productId: Int, newQuantity: Int)

    @Transaction
    suspend fun upsertItem(productId: Int, quantity: Int) {
        val existingItem = getItem(productId)
        if (existingItem != null) {
            updateQuantity(productId, quantity)
        } else {
            insert(CartEntity(productId = productId, quantity = quantity, totalPrice = 0.0))
        }
    }
}