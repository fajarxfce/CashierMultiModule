package com.fajarxfce.core.data.repository

import com.fajarxfce.core.data.network.NetworkResource
import com.fajarxfce.core.data.remote.AuthRemoteDataSource
import com.fajarxfce.core.datastore.NiaPreferencesDataSource
import com.fajarxfce.core.domain.repository.AuthRepository
import com.fajarxfce.core.model.data.auth.User
import com.fajarxfce.core.model.data.auth.request.LoginRequest
import com.fajarxfce.core.network.response.toUser
import com.fajarxfce.core.result.Result
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class DefaultAuthRepository @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val niaPreferencesDataSource: NiaPreferencesDataSource
) : AuthRepository {
    override fun login(
        username: String,
        password: String
    ): Flow<Result<User>> =
        object: NetworkResource<User>() {
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
                val response = remoteDataSource.login(request)
                return response.toUser()
            }

        }.asFlow()
}