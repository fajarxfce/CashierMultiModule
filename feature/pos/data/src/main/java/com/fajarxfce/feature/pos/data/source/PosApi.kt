package com.fajarxfce.feature.pos.data.source

import com.fajarxfce.core.network.model.BaseResponse
import com.fajarxfce.feature.pos.data.model.GetAllProductResponse
import com.fajarxfce.feature.pos.data.model.ProductDataItem
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

internal interface PosApi {
    @GET("product")
    suspend fun getProducts(
        @Query("orderBy") orderBy: String = "products.id",
        @Query("order") orderDirection: String = "asc",
        @Query("paginate") pageSize: Int = 10,
        @Query("page") pageNumber: Int = 1,
        @Header("Authorization") token: String
    ): BaseResponse<GetAllProductResponse>
}