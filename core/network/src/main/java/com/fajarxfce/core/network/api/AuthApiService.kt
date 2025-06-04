package com.fajarxfce.core.network.api

import com.fajarxfce.core.model.data.auth.request.LoginRequest
import com.fajarxfce.core.network.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse
}