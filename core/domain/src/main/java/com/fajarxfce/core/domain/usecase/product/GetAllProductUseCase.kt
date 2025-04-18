package com.fajarxfce.core.domain.usecase.product

import com.fajarxfce.core.domain.repository.ProductRepository
import javax.inject.Inject

class GetAllProductUseCase @Inject constructor(
    private val productRepository: ProductRepository,
) {
    operator fun invoke(params: GetAllProductParams = GetAllProductParams()) =
        productRepository.getAllProduct(params)
}
