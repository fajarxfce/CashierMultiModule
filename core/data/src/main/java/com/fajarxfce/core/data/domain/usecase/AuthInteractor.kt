package com.fajarxfce.core.data.domain.usecase

import com.fajarxfce.core.data.AuthRepository
import com.fajarxfce.core.model.data.auth.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val authRepository: AuthRepository
) : AuthUseCase{
    override fun login(
        username: String,
        password: String,
    ): Flow<User> {
        return authRepository.login(username, password)
    }
}