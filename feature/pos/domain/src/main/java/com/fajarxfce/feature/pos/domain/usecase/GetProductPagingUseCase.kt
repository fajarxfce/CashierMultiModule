package com.fajarxfce.feature.pos.domain.usecase

import androidx.paging.PagingData
import com.fajarxfce.feature.pos.domain.model.Product
import com.fajarxfce.feature.pos.domain.repository.PosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductPagingUseCase @Inject constructor(
    private val posRepository: PosRepository,
) {
    operator fun invoke(
        query: String? = null,
        categoryId: String? = null,
    ): Flow<PagingData<Product>> {
        return posRepository.getProducts(query = query, categoryId = categoryId)
    }
}