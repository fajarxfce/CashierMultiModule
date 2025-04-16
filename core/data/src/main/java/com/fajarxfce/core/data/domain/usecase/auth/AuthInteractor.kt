package com.fajarxfce.core.data.domain.usecase.auth

import com.fajarxfce.core.data.AuthRepository
import com.fajarxfce.core.model.data.auth.User
import com.fajarxfce.core.result.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val authRepository: AuthRepository
) : AuthUseCase{
    override fun login(email: String, password: String): Flow<Result<User>> {
        return authRepository.login(email, password)
    }
}