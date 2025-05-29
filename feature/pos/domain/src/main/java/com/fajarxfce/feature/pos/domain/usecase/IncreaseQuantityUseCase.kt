package com.fajarxfce.feature.pos.domain.usecase

import com.fajarxfce.feature.pos.domain.model.Cart
import com.fajarxfce.feature.pos.domain.repository.CartRepository
import javax.inject.Inject
import kotlin.random.Random

class IncreaseQuantityUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: Int, quantity: Int) {
        cartRepository.increaseQuantity(productId = productId, quantity = quantity)
    }
}