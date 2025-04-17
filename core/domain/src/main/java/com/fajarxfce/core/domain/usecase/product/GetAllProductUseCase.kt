package com.fajarxfce.core.domain.usecase.product

import com.fajarxfce.core.domain.repository.GetAllProductRepository
import javax.inject.Inject

class GetAllProductUseCase @Inject constructor(
    private val getAllProductRepository: GetAllProductRepository
) {
    operator fun invoke() = getAllProductRepository.getAllProduct()
}