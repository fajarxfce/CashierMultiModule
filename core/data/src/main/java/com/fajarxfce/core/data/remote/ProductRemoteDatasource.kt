package com.fajarxfce.core.data.remote

import com.fajarxfce.core.network.ProductApiService
import javax.inject.Inject

class ProductRemoteDatasource @Inject constructor(
    private val productApiService: ProductApiService
) {
    suspend fun getAllProduct() = productApiService.getAllProducts()
}