package com.fajarxfce.feature.cart.data.source

import com.fajarxfce.core.network.model.BaseResponse
import com.fajarxfce.core.result.Resource
import com.fajarxfce.feature.cart.data.model.CreateTransactionRequest
import com.fajarxfce.feature.cart.data.model.CreateTransactionResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface CartApi {
    @POST("transaction")
    suspend fun createTransaction(
        @Body request: CreateTransactionRequest
    ): BaseResponse<CreateTransactionResponse>
}