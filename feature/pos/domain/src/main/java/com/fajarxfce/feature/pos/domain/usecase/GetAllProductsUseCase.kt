package com.fajarxfce.feature.pos.domain.usecase

import com.fajarxfce.core.result.Resource
import com.fajarxfce.feature.pos.domain.model.Product
import com.fajarxfce.feature.pos.domain.repository.PosRepository
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(
    private val posRepository: PosRepository
) {
    suspend operator fun invoke(): Resource<List<Product>> = posRepository.getAllProduct()
}