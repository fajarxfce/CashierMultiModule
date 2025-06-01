package com.fajarxfce.feature.pos.data.source

import com.fajarxfce.core.network.model.BaseResponse
import com.fajarxfce.feature.pos.data.model.GetAllProductResponse
import com.fajarxfce.feature.pos.data.model.ProductDataItem
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.QueryMap

internal interface PosApi {
    @GET("product")
    suspend fun getProducts(
        @QueryMap(encoded = true) params: Map<String, String>
    ): BaseResponse<GetAllProductResponse>
}