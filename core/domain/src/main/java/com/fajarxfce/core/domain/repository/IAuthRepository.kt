package com.fajarxfce.core.domain.repository

import com.fajarxfce.core.model.data.auth.User
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    suspend fun login(username: String, password: String): Flow<Result<User>>
}