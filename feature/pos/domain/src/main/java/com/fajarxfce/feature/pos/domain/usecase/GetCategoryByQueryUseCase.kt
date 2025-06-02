package com.fajarxfce.feature.pos.domain.usecase

import androidx.paging.PagingData
import com.fajarxfce.feature.pos.domain.model.Category
import com.fajarxfce.feature.pos.domain.params.GetCategoryByQueryParams
import com.fajarxfce.feature.pos.domain.repository.PosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryByQueryUseCase @Inject constructor(
    private val posRepository: PosRepository
) {
    operator fun invoke(params: GetCategoryByQueryParams): Flow<PagingData<Category>>
        = posRepository.getCategoryByQuery(params)
}