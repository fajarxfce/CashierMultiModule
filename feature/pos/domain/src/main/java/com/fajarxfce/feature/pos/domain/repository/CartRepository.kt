package com.fajarxfce.feature.pos.domain.repository

import com.fajarxfce.core.result.Resource
import com.fajarxfce.core.model.cart.Cart

interface CartRepository {
    suspend fun insert(cart: Cart): Resource<Unit>
    suspend fun upsertItem(productId: Int, quantity: Int): Resource<Unit>
}