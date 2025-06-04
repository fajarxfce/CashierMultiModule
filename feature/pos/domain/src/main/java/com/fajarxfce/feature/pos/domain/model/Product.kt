package com.fajarxfce.feature.pos.domain.model

import com.fajarxfce.core.model.cart.CartItem

data class Product(
    val id: Int?,
    val name: String?,
    val description: String?,
    val price: Int?,
    val imageUrl: String?
)

fun Product.toCart(): CartItem = CartItem(
    productId = id,
    name = name,
    price = price,
    quantity = 1,
    imageUrl = imageUrl
)