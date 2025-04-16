package com.fajarxfce.core.network

import com.fajarxfce.core.network.response.ProductResponse
import retrofit2.http.GET

interface ProductApiService {
    @GET("product?orderBy=products.id&order=asc&paginate=10&page=1")
    suspend fun getAllProducts(): ProductResponse
}