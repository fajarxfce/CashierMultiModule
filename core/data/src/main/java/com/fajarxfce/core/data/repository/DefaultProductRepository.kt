package com.fajarxfce.core.data.repository

import com.fajarxfce.core.data.network.NetworkResource
import com.fajarxfce.core.data.remote.ProductRemoteDatasource
import com.fajarxfce.core.domain.repository.ProductRepository
import com.fajarxfce.core.domain.usecase.product.GetAllProductParams
import com.fajarxfce.core.model.data.product.Product
import com.fajarxfce.core.network.response.toProduct
import com.fajarxfce.core.result.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultProductRepository @Inject constructor(
    private val productRemoteDatasource: ProductRemoteDatasource
) : ProductRepository {
    override fun getAllProduct(params: GetAllProductParams): Flow<Result<List<Product>>> =
        object : NetworkResource<List<Product>>() {
            override suspend fun saveCallResult(data: List<Product>) {

            }

            override suspend fun createCall(): List<Product> {
                val response = productRemoteDatasource.getAllProduct(params = params)
                return response.toProduct()
            }

        }.asFlow()
}