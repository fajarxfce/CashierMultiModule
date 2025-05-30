package com.fajarxfce.feature.cart.domain.usecase

import com.fajarxfce.core.result.Resource
import com.fajarxfce.feature.cart.domain.model.CartItem
import com.fajarxfce.feature.cart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(): Flow<Resource<List<CartItem>>> {
        return cartRepository.getCartItems()
    }
}