package com.fajarxfce.feature.cart.data.repository

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
        return try {
            val itemToAdd = CartItemEntity(
                productId = product.id!!,
                name = product.name,
                price = product.price,
                quantity = quantity,
                imageUrl = product.imageUrl
            )
            cartDao.upsertIncrementQuantity(itemToAdd)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(BaseException("Failed to insert product to cart"))

        }
    }

    override suspend fun increaseProductQuantity(productId: Int): Resource<Unit> {
        return try {
            val result = cartDao.increaseProductQuantity(productId)
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(BaseException("Failed to increase product quantity"))
        }
    }

    override suspend fun decreaseProductQuantity(productId: Int): Resource<Unit> {
        return try {
            val result = cartDao.decreaseProductQuantity(productId)
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(BaseException("Failed to increase product quantity"))
        }
    }

    override suspend fun deleteCartItemByProductId(productId: Int): Resource<Unit> {
        return try {
            val result = cartDao.deleteCartItemByProductId(productId)
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(BaseException("Failed to delete product"))
        }
    }

}