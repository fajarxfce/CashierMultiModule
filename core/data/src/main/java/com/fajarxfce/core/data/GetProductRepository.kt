package com.fajarxfce.core.data

import com.fajarxfce.core.data.domain.repository.IGetProductRepository
import com.fajarxfce.core.model.data.product.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProductRepository @Inject constructor(

) : IGetProductRepository{
    override fun getAllProducts(): Flow<Result<List<Product>>> {
        return flow {  }
    }

}