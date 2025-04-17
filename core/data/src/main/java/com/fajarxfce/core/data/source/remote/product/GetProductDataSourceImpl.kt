package com.fajarxfce.core.data.source.remote.product

import com.fajarxfce.core.network.ProductApiService
import com.fajarxfce.core.network.response.ProductResponse
import javax.inject.Inject

class GetProductDataSourceImpl @Inject constructor(
    private val apiService: ProductApiService
) : GetProductDataSource {
    override suspend fun getAllProduct(): ProductResponse {
        return apiService.getAllProducts()
    }
}