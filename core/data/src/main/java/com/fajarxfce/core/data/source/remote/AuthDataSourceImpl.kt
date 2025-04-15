package com.fajarxfce.core.data.source.remote

import com.fajarxfce.core.model.data.auth.request.LoginRequest
import com.fajarxfce.core.network.AuthApiService
import com.fajarxfce.core.network.response.LoginResponse
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService,
) : AuthDataSource {

    override suspend fun login(request: LoginRequest): LoginResponse {
        return authApiService.login(request)
    }
}