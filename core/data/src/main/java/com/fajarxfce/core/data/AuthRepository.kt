package com.fajarxfce.core.data

import com.fajarxfce.core.AuthEventBus
import com.fajarxfce.core.data.domain.repository.IAuthRepository
import com.fajarxfce.core.data.source.remote.auth.AuthDataSource
import com.fajarxfce.core.data.util.NetworkResource
import com.fajarxfce.core.datastore.NiaPreferencesDataSource
import com.fajarxfce.core.model.data.auth.User
import com.fajarxfce.core.model.data.auth.request.LoginRequest
import com.fajarxfce.core.network.AuthApiService
import com.fajarxfce.core.network.response.toUser
import com.fajarxfce.core.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val niaPreferencesDataSource: NiaPreferencesDataSource,
) : IAuthRepository {
    override fun login(
        username: String,
        password: String,
    ): Flow<Result<User>> = object : NetworkResource<User>() {
        override suspend fun saveCallResult(data: User) {
            Timber.d("saveCallResult: $data")
            niaPreferencesDataSource.setAuthToken(data.token ?: "")
            niaPreferencesDataSource.setShouldHideOnboarding(true)

        }

        override suspend fun createCall(): User {
            val request = LoginRequest(
                email = username,
                password = password
            )
            val response = authDataSource.login(request)
            return response.toUser()
        }

    }.asFlow()

}