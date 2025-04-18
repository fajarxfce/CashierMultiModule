package com.fajarxfce.core.domain.repository

import androidx.paging.PagingData
import com.fajarxfce.core.domain.usecase.product.GetAllProductParams
import com.fajarxfce.core.model.data.product.Product
import com.fajarxfce.core.result.Result
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getAllProduct(params: GetAllProductParams): Flow<Result<List<Product>>>
    fun getProductPagingData(
        orderBy: String = "products.id",
        ascending: Boolean = true,
    ) : Flow<PagingData<Product>>
}