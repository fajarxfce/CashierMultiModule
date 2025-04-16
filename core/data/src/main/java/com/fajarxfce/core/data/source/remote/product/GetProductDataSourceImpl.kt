package com.fajarxfce.core.data.source.remote.product

import com.fajarxfce.core.network.ProductApiService
import com.fajarxfce.core.network.response.ProductResponse

class GetProductDataSourceImpl(
    private val apiService: ProductApiService
) : GetProductDataSource {
    override suspend fun getAllProduct(): ProductResponse {
        return apiService.getAllProducts()
    }
}