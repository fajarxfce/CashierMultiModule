package com.fajarxfce.core.domain.usecase.product

import androidx.paging.PagingData
import com.fajarxfce.core.domain.repository.ProductRepository
import com.fajarxfce.core.model.data.product.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagingProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(
        orderBy: String = "products.id",
        ascending: Boolean = true
    ): Flow<PagingData<Product>> {
        return productRepository.getProductPagingData(
            orderBy = orderBy,
            ascending = ascending
        )
    }
}