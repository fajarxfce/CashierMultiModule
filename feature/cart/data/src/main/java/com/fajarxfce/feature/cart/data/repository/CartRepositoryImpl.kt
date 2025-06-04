package com.fajarxfce.feature.cart.data.repository

import com.fajarxfce.core.domain.repository.CartRepository
import com.fajarxfce.core.model.cart.CartItem
import com.fajarxfce.feature.cart.data.source.local.CartDao
import com.fajarxfce.feature.cart.data.source.local.entity.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {
    override fun getCartItems(): Flow<List<CartItem>> {
        return cartDao.getCartItems().map { entities ->
            entities.map { it.toDomain() }
        }
    }
}