package com.fajarxfce.feature.login.data.repository

import android.util.Log
import com.fajarxfce.core.datastore.NiaPreferencesDataSource
import com.fajarxfce.core.network.safeApiCall
import com.fajarxfce.core.result.Resource
import com.fajarxfce.core.result.map
import com.fajarxfce.core.result.onFailure
import com.fajarxfce.core.result.onSuccess
import com.fajarxfce.feature.login.data.model.LoginRequest
import com.fajarxfce.feature.login.data.source.LoginApi
import com.fajarxfce.feature.login.domain.repository.LoginRepository
import timber.log.Timber
import javax.inject.Inject

internal class LoginRepositoryImpl @Inject constructor(
    private val api: LoginApi,
    private val preferencesDataSource: NiaPreferencesDataSource
) : LoginRepository{
    override suspend fun login(
        email: String,
        password: String,
    ): Resource<Unit> {
        val request = LoginRequest(
            email = email,
            password = password,
        )
        return safeApiCall { api.login(request) }.onSuccess {
            preferencesDataSource.setAuthToken(token = it.data?.token.toString())
            Log.d("LoginRepositoryImpl", "login: ${preferencesDataSource.getAuthToken()}")
        }.map {
            it.message.orEmpty()
        }
    }
}