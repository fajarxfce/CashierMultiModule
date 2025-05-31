package com.fajarxfce.feature.cart.data

import com.fajarxfce.core.model.entity.CartEntity
import com.fajarxfce.feature.cart.domain.model.CartItem

fun CartEntity.toCartItem(): CartItem {
    return CartItem(
        productId = productId,
        quantity = quantity,
        name = "",
    )
}

fun List<CartEntity>.toCartItems(): List<CartItem> {
    return map { it.toCartItem() }
}