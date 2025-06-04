package com.fajarxfce.core.domain.repository

import com.fajarxfce.core.model.cart.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<List<CartItem>>
}