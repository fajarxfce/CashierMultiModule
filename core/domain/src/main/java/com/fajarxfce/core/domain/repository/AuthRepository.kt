package com.fajarxfce.core.domain.repository

import com.fajarxfce.core.model.data.auth.User
import com.fajarxfce.core.result.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(username: String, password: String): Flow<Result<User>>
}