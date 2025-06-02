package com.fajarxfce.feature.pos.data.mapper

import com.fajarxfce.feature.pos.domain.model.Merk
import com.fajarxfce.feature.pos.data.model.ProductMerkDataItem

internal fun ProductMerkDataItem.toDomain(): Merk {

    return Merk(
        id = this.id,
        name = this.name,
        description = this.description,
        status = this.status,
    )
}

internal fun List<ProductMerkDataItem?>.toDomainList(): List<Merk> {
    return this.mapNotNull { it?.toDomain() }
}