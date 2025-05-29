package com.fajarxfce.feature.pos.domain.usecase

import com.fajarxfce.feature.pos.domain.model.Cart
import com.fajarxfce.feature.pos.domain.repository.CartRepository
import javax.inject.Inject
import kotlin.random.Random

class InsertProductToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: Int, quantity: Int) {
        cartRepository.insert(Cart(productId = Random.nextInt(1,5), quantity = quantity, totalPrice = 0.0))
    }
}