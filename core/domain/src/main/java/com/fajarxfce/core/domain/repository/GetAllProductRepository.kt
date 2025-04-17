package com.fajarxfce.core.domain.repository

import com.fajarxfce.core.model.data.product.Product
import com.fajarxfce.core.result.Result
import kotlinx.coroutines.flow.Flow

interface GetAllProductRepository {
    fun getAllProduct(): Flow<Result<List<Product>>>
}