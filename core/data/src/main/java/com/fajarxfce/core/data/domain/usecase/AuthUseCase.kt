package com.fajarxfce.core.data.domain.usecase

import com.fajarxfce.core.model.data.auth.User
import com.fajarxfce.core.result.Result
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {
    fun login(email: String, password: String): Flow<Result<User>>
}