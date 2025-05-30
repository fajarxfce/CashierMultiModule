package com.fajarxfce.feature.cart.domain.repository

import com.fajarxfce.core.result.Resource
import com.fajarxfce.feature.cart.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<Resource<List<CartItem>>>
}