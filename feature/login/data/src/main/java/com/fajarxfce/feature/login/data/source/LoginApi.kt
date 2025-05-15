package com.fajarxfce.feature.login.data.source

import com.fajarxfce.core.network.model.BaseResponse
import com.fajarxfce.feature.login.data.model.GetUserResponse
import com.fajarxfce.feature.login.data.model.LoginRequest
import com.fajarxfce.feature.login.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApi {
    @POST("/login")
    suspend fun login(@Body request: LoginRequest): BaseResponse<LoginResponse>

    @GET("/me")
    suspend fun getUserInformation(): BaseResponse<GetUserResponse>
}