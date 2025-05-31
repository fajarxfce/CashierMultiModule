package com.fajarxfce.feature.transactionhistory.data.source

import com.fajarxfce.core.network.model.BaseResponse
import com.fajarxfce.feature.transactionhistory.data.model.GetTansactionHistoryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TransactionHistoryApi {
    @GET("transaction")
    suspend fun getTransactions(
        @Query("orderBy") orderBy: String = "id",
        @Query("paginate") paginate: Int = 10,
        @Query("page") page: Int = 1,
    ): BaseResponse<GetTansactionHistoryResponse>
}