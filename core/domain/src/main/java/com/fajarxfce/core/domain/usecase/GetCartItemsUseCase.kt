package com.fajarxfce.core.domain.usecase

import com.fajarxfce.core.domain.repository.CartRepository
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke() = cartRepository.getCartItems()
}