package com.fajarxfce.feature.cart.domain.repository

import com.fajarxfce.core.result.Resource
import com.fajarxfce.feature.cart.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<List<CartItem>>
    suspend fun increaseProductQuantity(productId: Int): Resource<Unit>
    suspend fun decreaseProductQuantity(productId: Int): Resource<Unit>
    suspend fun createTransaction(cartItems: List<CartItem>): Resource<Unit>
}