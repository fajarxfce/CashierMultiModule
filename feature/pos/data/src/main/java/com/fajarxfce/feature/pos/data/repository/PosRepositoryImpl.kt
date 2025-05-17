package com.fajarxfce.feature.pos.data.repository

import com.fajarxfce.core.datastore.NiaPreferencesDataSource
import com.fajarxfce.core.network.safeApiCall
import com.fajarxfce.core.result.Resource
import com.fajarxfce.core.result.map
import com.fajarxfce.feature.pos.data.source.PosApi
import com.fajarxfce.feature.pos.domain.model.Product
import com.fajarxfce.feature.pos.domain.repository.PosRepository
import javax.inject.Inject

internal class PosRepositoryImpl @Inject constructor(
    private val api: PosApi,
    private val niaPreferencesDataSource: NiaPreferencesDataSource
) : PosRepository{
    override suspend fun getAllProduct(): Resource<List<Product>> {
        return safeApiCall { api.getProducts(
            token = "Bearer ${niaPreferencesDataSource.getAuthToken()}"
        ) }
            .map { response ->
                response.data?.data?.map { product ->
                    Product(
                        id = product?.id,
                        name = product?.name,
                        price = product?.price,
                        imageUrl = product?.updatedAt,
                        description = product?.description
                    )
                } ?: (emptyList())
            }
    }
}