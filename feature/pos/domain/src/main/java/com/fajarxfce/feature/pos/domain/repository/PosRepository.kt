package com.fajarxfce.feature.pos.domain.repository

import com.fajarxfce.core.result.Resource
import com.fajarxfce.feature.pos.domain.model.GetAllProductParams
import com.fajarxfce.feature.pos.domain.model.Product

interface PosRepository {
    suspend fun getAllProduct(): Resource<List<Product>>
}