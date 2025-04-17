package com.fajarxfce.core.network

import com.fajarxfce.core.network.response.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ProductApiService {
    @GET("product")
    suspend fun getAllProducts(
        @Query("orderBy") orderBy: String = "products.id",
        @Query("order") order: String = "asc",
        @Query("paginate") paginate: Int = 100,
        @Query("page") page: Int = 1,
        @Query("filter") filter: String? = null
    ): ProductResponse
}