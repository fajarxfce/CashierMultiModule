package com.fajarxfce.core.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "carts"
)
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    val productId: Int?,
    val quantity: Int?,
    val totalPrice: Double?
)