package com.fajarxfce.feature.cart.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fajarxfce.core.model.cart.CartItem

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val productId: Int,
    val name: String? = null,
    val price: Int? = null,
    var quantity: Int? = null,
    val imageUrl: String? = null,
)

fun CartItemEntity.toDomain(): CartItem = CartItem(
    productId = productId,
    name = name,
    price = price,
    quantity = quantity,
    imageUrl = imageUrl,
)

fun CartItem.toEntity(): CartItemEntity = CartItemEntity(
    productId = productId!!,
    name = name,
    price = price,
    quantity = quantity,
    imageUrl = imageUrl,
)