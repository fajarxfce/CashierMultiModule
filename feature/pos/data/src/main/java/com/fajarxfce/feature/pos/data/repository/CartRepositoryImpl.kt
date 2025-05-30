package com.fajarxfce.feature.pos.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.fajarxfce.core.database.dao.CartDao
import com.fajarxfce.core.exception.BaseException
import com.fajarxfce.core.result.Resource
import com.fajarxfce.core.model.cart.Cart
import com.fajarxfce.core.model.cart.toEntity
import com.fajarxfce.feature.pos.domain.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
) : CartRepository {
    override suspend fun insert(cart: Cart): Resource<Unit> {
        return try {
            cartDao.insert(cart.toEntity())
            Resource.Success(Unit)
        } catch (e: SQLiteConstraintException) {
            Resource.Error(BaseException("Product already in cart"))
        } catch (e: Exception) {
            Resource.Error(BaseException(e.message ?: "Unknown error"))
        }
    }

    override suspend fun upsertItem(
        productId: Int,
        quantity: Int,
    ): Resource<Unit> {
        return try {
            cartDao.upsertItem(
                productId = productId,
                quantity = quantity,
            )
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(BaseException(e.message ?: "Unknown error"))
        }
    }
}