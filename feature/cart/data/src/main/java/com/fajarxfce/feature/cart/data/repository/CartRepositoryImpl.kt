package com.fajarxfce.feature.cart.data.repository

import com.fajarxfce.core.database.safeDbCall
import com.fajarxfce.core.domain.repository.CartRepository
import com.fajarxfce.core.exception.BaseException
import com.fajarxfce.core.model.cart.CartItem
import com.fajarxfce.core.model.product.Product
import com.fajarxfce.core.result.Resource
import com.fajarxfce.feature.cart.data.source.local.dao.CartDao
import com.fajarxfce.feature.cart.data.source.local.entity.CartItemEntity
import com.fajarxfce.feature.cart.data.source.local.entity.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
) : CartRepository {
    override fun getCartItems(): Flow<List<CartItem>> {
        return cartDao.getCartItems().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun upsertProductToCart(product: Product, quantity: Int): Resource<Unit> {
        val itemToAdd = CartItemEntity(
            productId = product.id!!,
            name = product.name,
            price = product.price,
            quantity = quantity,
            imageUrl = product.imageUrl
        )
        return safeDbCall { cartDao.upsertIncrementQuantity(itemToAdd) }
    }

    override suspend fun increaseProductQuantity(productId: Int): Resource<Unit> {
        return safeDbCall { cartDao.increaseProductQuantity(productId) }
    }

    override suspend fun decreaseProductQuantity(productId: Int): Resource<Unit> {
        return safeDbCall { cartDao.decreaseProductQuantity(productId) }
    }

    override suspend fun deleteCartItemByProductId(productId: Int): Resource<Unit> {
        return safeDbCall { cartDao.deleteCartItemByProductId(productId) }
    }

}