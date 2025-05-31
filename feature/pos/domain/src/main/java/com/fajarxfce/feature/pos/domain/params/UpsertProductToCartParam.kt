package com.fajarxfce.feature.pos.domain.params

data class UpsertProductToCartParam(
    val productId: Int?,
    val name: String?,
    val price: Int?,
    val quantity: Int?,
    val imageUrl: String?
)