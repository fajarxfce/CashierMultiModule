package com.fajarxfce.feature.pos.domain.usecase

import com.fajarxfce.core.model.cart.Cart
import com.fajarxfce.core.result.Resource
import com.fajarxfce.feature.pos.domain.params.UpsertProductToCartParam
import com.fajarxfce.feature.pos.domain.repository.CartRepository
import javax.inject.Inject

class UpsertProductToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(param: UpsertProductToCartParam): Resource<Unit> =
        cartRepository.upsertItem(param)
}