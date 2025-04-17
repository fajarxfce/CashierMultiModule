package com.fajarxfce.core.data.domain.repository

import com.fajarxfce.core.model.data.product.Product
import com.fajarxfce.core.result.Result
import kotlinx.coroutines.flow.Flow

interface IGetProductRepository {
    fun getAllProducts(): Flow<Result<List<Product>>>
}