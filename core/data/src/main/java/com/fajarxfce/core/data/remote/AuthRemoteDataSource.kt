package com.fajarxfce.core.data.remote

import com.fajarxfce.core.model.data.auth.request.LoginRequest
import com.fajarxfce.core.network.AuthApiService
import com.fajarxfce.core.network.response.LoginResponse
import jakarta.inject.Inject


class AuthRemoteDataSource @Inject constructor(
    private val authService: AuthApiService
) {
    suspend fun login(request: LoginRequest): LoginResponse = authService.login(request)
}