package com.fajarxfce.core.data.domain.repository

import com.fajarxfce.core.model.data.auth.User
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    fun login(username: String, password: String): Flow<User>
}