package com.fajarxfce.core.data.domain.usecase.auth

import com.fajarxfce.core.data.AuthRepository
import com.fajarxfce.core.datastore.NiaPreferencesDataSource
import com.fajarxfce.core.model.data.auth.User
import com.fajarxfce.core.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val authRepository: AuthRepository,
    private val preferencesDataSource: NiaPreferencesDataSource
) : AuthUseCase{
    override fun login(email: String, password: String): Flow<Result<User>> {
        return authRepository.login(email, password)
            .onEach { result ->
                if (result is Result.Success){
                    preferencesDataSource.setLoginState(true)
                }
            }
    }
}