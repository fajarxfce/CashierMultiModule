package com.fajarxfce.feature.cart.domain.usecase

import com.fajarxfce.core.result.Resource
import com.fajarxfce.feature.cart.domain.repository.CartRepository
import javax.inject.Inject

class IncreaseProductQuantityUseCase @Inject constructor(
    private val cartRepository: CartRepository,
) {
    suspend operator fun invoke(productId: Int): Resource<Unit> {
        return cartRepository.increaseProductQuantity(productId)
    }
}