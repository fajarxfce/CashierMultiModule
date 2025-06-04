package com.fajarxfce.feature.cart.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.fajarxfce.feature.cart.data.source.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getCartItems(): Flow<List<CartItemEntity>>

    @Query("SELECT SUM(quantity) FROM cart_items")
    fun getCartItemCount(): Flow<Int?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewCartItem(item: CartItemEntity): Long

    @Update
    suspend fun updateExistingCartItem(item: CartItemEntity)

    @Query("SELECT * FROM cart_items WHERE productId = :productId")
    suspend fun getCartItemByProductId(productId: Int): CartItemEntity?

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun deleteCartItemByProductId(productId: Int)

    @Query("DELETE FROM cart_items")
    suspend fun clearAllCartItems()

    @Transaction
    suspend fun upsertIncrementQuantity(itemToAdd: CartItemEntity) {
        val existingItem = getCartItemByProductId(itemToAdd.productId)
        if (existingItem == null) {
            insertNewCartItem(itemToAdd)
        } else {
            val updatedQuantity = existingItem.quantity!! + itemToAdd.quantity!!
            updateExistingCartItem(existingItem.copy(quantity = updatedQuantity))
        }
    }
}