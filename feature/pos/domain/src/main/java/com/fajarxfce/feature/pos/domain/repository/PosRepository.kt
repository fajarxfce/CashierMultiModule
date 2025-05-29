package com.fajarxfce.feature.pos.domain.repository

import androidx.paging.PagingData
import com.fajarxfce.feature.pos.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface PosRepository {
    fun getProducts(
        query: String? = null,
        categoryId: String? = null,
    ): Flow<PagingData<Product>>
}