package com.fajarxfce.core.data.source.remote.auth

import com.fajarxfce.core.model.data.auth.request.LoginRequest
import com.fajarxfce.core.network.response.LoginResponse

interface AuthDataSource {
    suspend fun login(request: LoginRequest): LoginResponse
}