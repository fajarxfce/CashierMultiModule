package com.fajarxfce.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartItem(
    val productId: String,
    val name: String,
    val price: Double,
    var quantity: Int,
    val imageUrl: String? = null,
) : Parcelable {
    val subtotal: Double
        get() = price * quantity
}