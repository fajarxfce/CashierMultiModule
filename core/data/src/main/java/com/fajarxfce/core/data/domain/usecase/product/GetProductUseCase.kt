package com.fajarxfce.core.data.domain.usecase.product

import com.fajarxfce.core.model.data.product.Product
import com.fajarxfce.core.result.Result
import kotlinx.coroutines.flow.Flow

interface GetProductUseCase {
    fun getAllProduct(): Flow<Result<List<Product>>>
}