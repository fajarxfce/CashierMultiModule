package com.fajarxfce.core.data

import com.fajarxfce.core.AuthEventBus
import com.fajarxfce.core.data.domain.repository.IGetProductRepository
import com.fajarxfce.core.data.source.remote.product.GetProductDataSource
import com.fajarxfce.core.data.util.NetworkResource
import com.fajarxfce.core.model.data.product.Product
import com.fajarxfce.core.network.response.toProduct
import com.fajarxfce.core.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProductRepository @Inject constructor(
    private val getProductDataSource: GetProductDataSource,
) : IGetProductRepository {
    override fun getAllProducts(): Flow<Result<List<Product>>> =
        object : NetworkResource<List<Product>>() {
            override suspend fun saveCallResult(data: List<Product>) {
                data.forEach {
                    Timber.d("saveCallResult: ${it.name}")
                }
            }

            override suspend fun createCall(): List<Product> {
                val response = getProductDataSource.getAllProduct()
                return response.toProduct()
            }

        }.asFlow()

}