package com.fajarxfce.core.data

import com.fajarxfce.core.data.domain.repository.IAuthRepository
import com.fajarxfce.core.model.data.auth.User
import com.fajarxfce.core.model.data.auth.request.LoginRequest
import com.fajarxfce.core.network.AuthApiService
import com.fajarxfce.core.network.response.toUser
import com.fajarxfce.core.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService
) : IAuthRepository {
    override fun login(
        username: String,
        password: String,
    ): Flow<Result<User>> = flow {
        emit(Result.Loading)
        try {
            val request = LoginRequest(username, password)
            val response = authApiService.login(request)
            emit(Result.Success(response.toUser()))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

}