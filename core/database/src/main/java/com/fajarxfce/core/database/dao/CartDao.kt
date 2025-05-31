package com.fajarxfce.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.fajarxfce.core.model.cart.Cart
import com.fajarxfce.core.model.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM carts where productId = :productId")
    fun getItem(productId: Int): CartEntity?

    @Query("SELECT * FROM carts")
    fun getAll(): Flow<List<CartEntity>>

    @Query("SELECT COUNT(*) FROM carts")
    fun count(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartEntity: CartEntity)

    @Query("UPDATE carts SET quantity = quantity + 1 WHERE productId = :productId")
    suspend fun increaseProductQuantity(productId: Int)

    @Query("UPDATE carts SET quantity = quantity - 1 WHERE productId = :productId")
    suspend fun decreaseProductQuantity(productId: Int)

    @Query("SELECT * FROM carts WHERE productId = :productId")
    fun getProductById(productId: Int): CartEntity?

    @Query("UPDATE carts SET quantity = quantity + :newQuantity WHERE productId = :productId")
    suspend fun updateQuantity(productId: Int, newQuantity: Int)

    @Transaction
    suspend fun upsertItem(cart: Cart) {
        val existingItem = getItem(cart.productId ?: 0)
        if (existingItem != null) {
            updateQuantity(cart.productId ?: 0, cart.quantity ?: 0)
        } else {
            insert(
                CartEntity(
                    productId = cart.productId,
                    quantity = cart.quantity,
                    imageUrl = cart.imageUrl,
                    name = cart.name,
                    price = cart.price,
                ),
            )
        }
    }
}