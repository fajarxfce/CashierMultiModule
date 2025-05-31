package com.fajarxfce.feature.cart.data.repository

import com.fajarxfce.core.database.dao.CartDao
import com.fajarxfce.core.exception.BaseException
import com.fajarxfce.core.result.Resource
import com.fajarxfce.feature.cart.data.toCartItems
import com.fajarxfce.feature.cart.domain.model.CartItem
import com.fajarxfce.feature.cart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {
    override fun getCartItems(): Flow<List<CartItem>> = flow {
        emitAll(cartDao.getAll().map { it.toCartItems() })
    }

    override suspend fun increaseProductQuantity(productId: Int): Resource<Unit> {
        return try {
            Resource.Success(cartDao.increaseProductQuantity(productId))
        } catch (e: Exception) {
            Resource.Error(BaseException("Error increasing product quantity"))
        }
    }

    override suspend fun decreaseProductQuantity(productId: Int): Resource<Unit> {
        return try {
            Resource.Success(cartDao.decreaseProductQuantity(productId))
        } catch (e: Exception) {
            Resource.Error(BaseException("Error decreasing product quantity"))
        }
    }
}