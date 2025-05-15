package com.fajarxfce.feature.login.domain.repository

import com.fajarxfce.core.result.Resource

interface LoginRepository {
    suspend fun login(
        email: String,
        password: String
    ): Resource<Unit>
}