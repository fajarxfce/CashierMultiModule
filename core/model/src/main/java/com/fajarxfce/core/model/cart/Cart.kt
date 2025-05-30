package com.fajarxfce.core.model.cart

import com.fajarxfce.core.model.entity.CartEntity

data class Cart(
    val productId: Int?,
    val quantity: Int?,
    val totalPrice: Double?
)

fun Cart.toEntity(): CartEntity = CartEntity(
    productId = productId,
    quantity = quantity,
    totalPrice = totalPrice
)