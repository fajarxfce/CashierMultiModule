package com.fajarxfce.core.domain.usecase.product

data class GetAllProductParams(
    val order: OrderOption = OrderOption(),
    val orderBy: String = "products.id",
    val page: Int = 1,
    val paginate: Int = 5,
)

data class OrderOption(
    val ascending: Boolean = true,
)
