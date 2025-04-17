package com.fajarxfce.core.data.repository

import com.fajarxfce.core.data.network.NetworkResource
import com.fajarxfce.core.data.remote.ProductRemoteDatasource
import com.fajarxfce.core.domain.repository.GetAllProductRepository
import com.fajarxfce.core.model.data.product.Product
import com.fajarxfce.core.network.response.toProduct
import com.fajarxfce.core.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultGetAllProductRepository @Inject constructor(
    private val productRemoteDatasource: ProductRemoteDatasource
) : GetAllProductRepository {
    override fun getAllProduct(): Flow<Result<List<Product>>> =
        object : NetworkResource<List<Product>>() {
            override suspend fun saveCallResult(data: List<Product>) {

            }

            override suspend fun createCall(): List<Product> {
                val response = productRemoteDatasource.getAllProduct()
                return response.toProduct()
            }

        }.asFlow()
}