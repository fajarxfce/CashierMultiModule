package com.fajarxfce.core.model.cart

import com.fajarxfce.core.model.entity.CartEntity

data class Cart(
    val productId: Int?,
    val name: String?,
    val price: Int?,
    val quantity: Int?,
    val imageUrl: String?,
)

fun Cart.toEntity(): CartEntity = CartEntity(
    productId = productId,
    quantity = quantity,
    name = name,
    price = price,
    imageUrl = imageUrl
)