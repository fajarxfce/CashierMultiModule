package com.fajarxfce.feature.login.domain.usecase

import com.fajarxfce.core.result.Resource
import com.fajarxfce.feature.login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ) : Resource<Unit> =
        loginRepository.login(email = email, password = password)
}