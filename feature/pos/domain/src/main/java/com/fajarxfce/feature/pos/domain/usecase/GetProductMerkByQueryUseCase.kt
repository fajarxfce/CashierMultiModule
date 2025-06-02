package com.fajarxfce.feature.pos.domain.usecase

import androidx.paging.PagingData
import com.fajarxfce.feature.pos.domain.model.Category
import com.fajarxfce.feature.pos.domain.model.Merk
import com.fajarxfce.feature.pos.domain.params.GetCategoryByQueryParams
import com.fajarxfce.feature.pos.domain.params.GetProductMerkByQueryParams
import com.fajarxfce.feature.pos.domain.repository.PosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductMerkByQueryUseCase @Inject constructor(
    private val posRepository: PosRepository
) {
    operator fun invoke(params: GetProductMerkByQueryParams): Flow<PagingData<Merk>>
        = posRepository.getProductMerkByQuery(params)
}