package com.fajarxfce.feature.pos.data.model

data class GetAllProductRequest(
    val page: Int,
    val limit: Int,
    val search: String? = null,
)