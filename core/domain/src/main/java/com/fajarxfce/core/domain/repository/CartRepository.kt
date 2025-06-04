package com.fajarxfce.core.domain.repository

import com.fajarxfce.core.model.cart.CartItem
import com.fajarxfce.core.model.product.Product
import com.fajarxfce.core.result.Resource
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<List<CartItem>>
    suspend fun upsertProductToCart(product: Product, quantity: Int): Resource<Unit>
    suspend fun increaseProductQuantity(productId: Int): Resource<Unit>
    suspend fun decreaseProductQuantity(productId: Int): Resource<Unit>
    suspend fun deleteCartItemByProductId(productId: Int): Resource<Unit>
}