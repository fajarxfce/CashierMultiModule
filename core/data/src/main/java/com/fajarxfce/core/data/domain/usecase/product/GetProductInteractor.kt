package com.fajarxfce.core.data.domain.usecase.product

import com.fajarxfce.core.model.data.product.Product
import com.fajarxfce.core.result.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductInteractor @Inject constructor(

) : GetProductUseCase {
    override fun getAllProduct(): Flow<Result<List<Product>>> {
        TODO("Not yet implemented")
    }
}