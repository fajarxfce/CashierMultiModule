package com.fajarxfce.core.domain.usecase

import com.fajarxfce.core.domain.repository.CartRepository
import com.fajarxfce.core.model.cart.CartItem
import com.fajarxfce.core.model.product.Product
import com.fajarxfce.core.result.Resource
import javax.inject.Inject

class DecreaseProductQuantityUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: Int) : Resource<Unit> {
        return cartRepository.decreaseProductQuantity(productId)
    }
}