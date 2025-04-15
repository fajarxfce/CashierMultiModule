package com.fajarxfce.core.data.domain.usecase

import com.fajarxfce.core.model.data.auth.User
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {
    fun login(username: String, password: String): Flow<User>
}