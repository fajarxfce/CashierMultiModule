package com.fajarxfce.feature.pos.data.mapper

import com.fajarxfce.feature.pos.data.model.CategoryDataItem
import com.fajarxfce.feature.pos.domain.model.Category

internal fun CategoryDataItem.toDomain(): Category {

    return Category(
        id = this.id,
        name = this.name,
        description = this.description,
        status = this.status,
    )
}

internal fun List<CategoryDataItem?>.toDomainList(): List<Category> {
    return this.mapNotNull { it?.toDomain() }
}