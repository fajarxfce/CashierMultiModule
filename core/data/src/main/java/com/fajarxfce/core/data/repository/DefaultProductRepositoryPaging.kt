package com.fajarxfce.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fajarxfce.core.data.ProductPagingSource
import com.fajarxfce.core.data.network.NetworkResource
import com.fajarxfce.core.data.remote.ProductRemoteDatasource
import com.fajarxfce.core.domain.repository.ProductRepository
import com.fajarxfce.core.domain.usecase.product.GetAllProductParams
import com.fajarxfce.core.model.data.product.Product
import com.fajarxfce.core.network.response.toProduct
import com.fajarxfce.core.result.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultProductRepositoryPaging @Inject constructor(
    private val productRemoteDatasource: ProductRemoteDatasource
) : ProductRepository {
    override fun getAllProduct(params: GetAllProductParams): Flow<Result<List<Product>>> =
        object : NetworkResource<List<Product>>() {
            override suspend fun saveCallResult(data: List<Product>) {
                // Implementation
            }

            override suspend fun createCall(): List<Product> {
                val response = productRemoteDatasource.getAllProduct(params = params)
                return response.toProduct()
            }
        }.asFlow()

    override fun getProductPagingData(
        orderBy: String,
        ascending: Boolean
    ): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ProductPagingSource(
                    remoteDatasource = productRemoteDatasource,
                    orderBy = orderBy,
                    ascending = ascending
                )
            }
        ).flow
    }
}