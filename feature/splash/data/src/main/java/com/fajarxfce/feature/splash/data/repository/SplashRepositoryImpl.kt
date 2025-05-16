package com.fajarxfce.feature.splash.data.repository

import android.util.Log
import com.fajarxfce.core.datastore.NiaPreferencesDataSource
import com.fajarxfce.core.network.safeApiCall
import com.fajarxfce.core.result.Resource
import com.fajarxfce.core.result.onSuccess
import com.fajarxfce.core.result.toUnit
import com.fajarxfce.feature.splash.data.source.SplashApi
import com.fajarxfce.feature.splash.domain.repository.SplashRepository
import javax.inject.Inject

class SplashRepositoryImpl @Inject constructor(
    private val api : SplashApi,
    private val niaPreferencesDataSource: NiaPreferencesDataSource
) : SplashRepository {
    override suspend fun CheckUserLoggedIn(): Resource<Unit> {
        val token = niaPreferencesDataSource.getAuthToken()
        Log.d("SplashRepositoryImpl", "CheckUserLoggedIn: $token")
        return safeApiCall { api.checkToken("Bearer $token") }
            .onSuccess {

            }.toUnit()
    }

}