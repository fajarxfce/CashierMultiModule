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
        @Query("paginate") pageSize: Int,
        @Query("page") pageNumber: Int,
        @Query("search") query: String? = null,
        @Query("category_id") categoryId: String? = null,
    ): BaseResponse<GetAllProductResponse>
}