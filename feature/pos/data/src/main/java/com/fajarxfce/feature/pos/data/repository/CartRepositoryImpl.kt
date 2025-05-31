package com.fajarxfce.feature.pos.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.fajarxfce.core.database.dao.CartDao
import com.fajarxfce.core.exception.BaseException
import com.fajarxfce.core.result.Resource
import com.fajarxfce.core.model.cart.Cart
import com.fajarxfce.core.model.cart.toEntity
import com.fajarxfce.feature.pos.domain.params.UpsertProductToCartParam
import com.fajarxfce.feature.pos.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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
        param: UpsertProductToCartParam,
    ): Resource<Unit> {
        return try {
            cartDao.upsertItem(
                Cart(
                    productId = param.productId,
                    name = param.name,
                    price = param.price,
                    quantity = param.quantity,
                    imageUrl = param.imageUrl,
                ),
            )
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(BaseException(e.message ?: "Unknown error"))
        }
    }

    override fun getTotalCartItems(): Flow<Int> = flow {
        emitAll(cartDao.count())
    }
}