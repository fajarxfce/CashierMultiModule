package com.fajarxfce.core.domain.model

data class GetAllProductsRequest(
    val orderBy: String = "products.id",
    val orderDirection: String = "asc",
    val paginate: Int = 10,
    val page: Int = 1
)