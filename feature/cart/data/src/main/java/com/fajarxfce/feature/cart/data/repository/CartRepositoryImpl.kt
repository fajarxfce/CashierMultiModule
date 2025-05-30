package com.fajarxfce.feature.cart.data.repository

import com.fajarxfce.core.database.dao.CartDao
import com.fajarxfce.core.exception.BaseException
import com.fajarxfce.core.model.cart.Cart
import com.fajarxfce.core.result.Resource
import com.fajarxfce.feature.cart.domain.model.CartItem
import com.fajarxfce.feature.cart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {
    override fun getCartItems(): Flow<Resource<List<CartItem>>> = flow {
        emit(Resource.Error(BaseException("Not implemented")))
    }
}