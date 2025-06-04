package com.fajarxfce.core.domain.usecase

import com.fajarxfce.core.domain.repository.CartRepository
import com.fajarxfce.core.model.cart.CartItem
import com.fajarxfce.core.model.product.Product
import com.fajarxfce.core.result.Resource
import javax.inject.Inject

class UpsertProductToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(product: Product, quantity: Int) : Resource<Unit> {
        return cartRepository.upsertProductToCart(product, quantity)
    }
}