package com.fajarxfce.feature.pos.domain.model

import com.fajarxfce.core.database.entity.CartEntity

data class Cart(
    val productId: Int,
    val quantity: Int,
    val totalPrice: Double
)

fun Cart.toEntity(): CartEntity = CartEntity(
        productId = productId,
        quantity = quantity,
        totalPrice = totalPrice
    )