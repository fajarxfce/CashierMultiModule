package com.fajarxfce.core.model.cart


data class CartItem(
    val productId: Int? = null,
    val name: String? = null,
    val price: Int? = null,
    var quantity: Int? = null,
    val imageUrl: String? = null,
)