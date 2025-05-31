package com.fajarxfce.feature.pos.domain.repository

import com.fajarxfce.core.result.Resource
import com.fajarxfce.core.model.cart.Cart
import com.fajarxfce.feature.pos.domain.params.UpsertProductToCartParam
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun insert(cart: Cart): Resource<Unit>
    suspend fun upsertItem(param: UpsertProductToCartParam): Resource<Unit>
    fun getTotalCartItems(): Flow<Int>
}