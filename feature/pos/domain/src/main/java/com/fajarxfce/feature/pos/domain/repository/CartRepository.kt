package com.fajarxfce.feature.pos.domain.repository

import com.fajarxfce.core.result.Resource
import com.fajarxfce.feature.pos.domain.model.Cart

interface CartRepository {
    suspend fun insert(cart: Cart): Resource<Unit>
}