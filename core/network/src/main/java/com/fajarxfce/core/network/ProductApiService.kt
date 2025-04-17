package com.fajarxfce.core.network

import com.fajarxfce.core.network.response.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface ProductApiService {
    @GET("product?orderBy=products.id&order=asc&paginate=10&page=1")
    @Headers("X-API-KEY: 22|RQskfspd6VlA1qHkvvzLEbsu9LUzqSCjRKkV8tJF839e8aae")
    suspend fun getAllProducts(): ProductResponse
}