package com.fajarxfce.feature.cart.domain.usecase

import com.fajarxfce.feature.cart.domain.model.CartItem
import com.fajarxfce.feature.cart.domain.repository.CartRepository
import javax.inject.Inject

class CreateTransactionUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(cartItems: List<CartItem>) = cartRepository.createTransaction(cartItems)
}