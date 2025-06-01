package com.fajarxfce.feature.pos.domain.repository

import androidx.paging.PagingData
import com.fajarxfce.feature.pos.domain.model.Product
import com.fajarxfce.feature.pos.domain.params.GetAllProductParams
import kotlinx.coroutines.flow.Flow

interface PosRepository {
    fun getProducts(
        params: GetAllProductParams
    ): Flow<PagingData<Product>>
}