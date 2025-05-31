package com.fajarxfce.feature.pos.domain.usecase

import com.fajarxfce.feature.pos.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTotalCartItemsUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(): Flow<Int> {
        return cartRepository.getTotalCartItems()
    }
}