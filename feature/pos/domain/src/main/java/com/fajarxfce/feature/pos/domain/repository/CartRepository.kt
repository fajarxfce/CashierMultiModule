package com.fajarxfce.feature.pos.domain.repository

import com.fajarxfce.core.result.Resource
import com.fajarxfce.core.model.cart.Cart
import com.fajarxfce.feature.pos.domain.params.UpsertProductToCartParam

interface CartRepository {
    suspend fun insert(cart: Cart): Resource<Unit>
    suspend fun upsertItem(param: UpsertProductToCartParam): Resource<Unit>
}