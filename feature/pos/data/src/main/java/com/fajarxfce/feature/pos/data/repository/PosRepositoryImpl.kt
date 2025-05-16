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
        ) }.map {
            listOf(
                Product(
                    id = 1,
                    name = "Product 1",
                    description = "",
                    price = 2222.0,
                    imageUrl = "",
                ),
                Product(
                    id = 2,
                    name = "Product 2",
                    description = "",
                    price = 2222.0,
                    imageUrl = "",
                ),
                Product(
                    id = 3,
                    name = "Product 3",
                    description = "",
                    price = 2222.0,
                    imageUrl = "",
                ),
                Product(
                    id = 4,
                    name = "Product 4",
                    description = "",
                    price = 2222.0,
                    imageUrl = "",
                ),
                Product(
                    id = 5,
                    name = "Product 5",
                    description = "",
                    price = 2222.0,
                    imageUrl = "",
                ),
                Product(
                    id = 6,
                    name = "Product 6",
                    description = "",
                    price = 2222.0,
                    imageUrl = "",
                ),
                Product(
                    id = 7,
                    name = "Product 7",
                    description = "",
                    price = 2222.0,
                    imageUrl = "",
                )
            )

        }
    }
}