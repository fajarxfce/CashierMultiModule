package com.fajarxfce.feature.splash.data.source

import com.fajarxfce.core.network.model.BaseResponse
import com.fajarxfce.feature.splash.data.model.CheckTokenRequest
import com.fajarxfce.feature.splash.data.model.CheckTokenResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface SplashApi {
    @GET("me")
    suspend fun checkToken(
        @Header("Authorization") token: CheckTokenRequest,
    ) : BaseResponse<CheckTokenResponse>
}