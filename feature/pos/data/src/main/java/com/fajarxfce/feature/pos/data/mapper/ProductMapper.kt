package com.fajarxfce.feature.pos.data.mapper

import com.fajarxfce.feature.pos.data.model.ProductDataItem
import com.fajarxfce.core.model.product.Product

internal fun ProductDataItem.toDomain(): Product {

    return Product(
        id = this.id,
        name = this.name,
        description = this.description,
        price = this.price,
        imageUrl = this.media?.getOrNull(0)?.originalUrl
    )
}

internal fun List<ProductDataItem?>.toDomainList(): List<Product> {
    return this.mapNotNull { it?.toDomain() }
}