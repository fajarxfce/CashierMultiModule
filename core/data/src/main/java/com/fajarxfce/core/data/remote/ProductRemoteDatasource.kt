package com.fajarxfce.core.data.remote

import com.fajarxfce.core.domain.usecase.product.GetAllProductParams
import com.fajarxfce.core.network.ProductApiService
import com.fajarxfce.core.network.response.ProductResponse
import javax.inject.Inject

class ProductRemoteDatasource @Inject constructor(
    private val productApiService: ProductApiService
) {
    suspend fun getAllProduct(params: GetAllProductParams) : ProductResponse {
        val sortOrder = if (params.order.ascending) "asc" else "desc"

        return productApiService.getAllProducts(
            page = params.page,
            order = sortOrder,
            orderBy = params.orderBy,
            paginate = params.paginate,
        )

    }
}