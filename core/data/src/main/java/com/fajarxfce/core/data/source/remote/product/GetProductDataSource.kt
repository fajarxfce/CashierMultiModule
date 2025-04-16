package com.fajarxfce.core.data.source.remote.product

import com.fajarxfce.core.model.data.product.Product
import com.fajarxfce.core.network.response.ProductResponse

interface GetProductDataSource{
    suspend fun getAllProduct(): ProductResponse
}