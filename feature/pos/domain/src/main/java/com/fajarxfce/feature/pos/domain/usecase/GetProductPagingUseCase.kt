package com.fajarxfce.feature.pos.domain.usecase

import androidx.paging.PagingData
import com.fajarxfce.feature.pos.domain.model.Product
import com.fajarxfce.feature.pos.domain.params.GetAllProductParams
import com.fajarxfce.feature.pos.domain.repository.PosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductPagingUseCase @Inject constructor(
    private val posRepository: PosRepository,
) {
    operator fun invoke(
        params: GetAllProductParams
    ): Flow<PagingData<Product>> {
        return posRepository.getProducts(params)
    }
}