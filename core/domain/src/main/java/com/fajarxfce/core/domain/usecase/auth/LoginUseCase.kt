package com.fajarxfce.core.domain.usecase.auth

import com.fajarxfce.core.datastore.NiaPreferencesDataSource
import com.fajarxfce.core.domain.repository.AuthRepository
import com.fajarxfce.core.model.data.auth.User
import com.fajarxfce.core.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val niaPreferencesDataSource: NiaPreferencesDataSource
) {
    operator fun invoke(username: String, password: String): Flow<Result<User>> {
        return authRepository.login(username, password).onEach {
            if (it is Result.Success){
                niaPreferencesDataSource.setLoginState(true)
            }
        }
    }
}