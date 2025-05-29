package com.fajarxfce.feature.pos.domain.usecase

import com.fajarxfce.core.result.Resource
import com.fajarxfce.feature.pos.domain.repository.CartRepository
import javax.inject.Inject

class UpsertProductToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: Int, quantity: Int): Resource<Unit> =
        cartRepository.upsertItem(productId, quantity)
}