package com.fajarxfce.core.network

import android.util.Log
import com.fajarxfce.core.datastore.NiaPreferencesDataSource
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthInterceptor @Inject constructor(
    private val niaPreferencesDataSource: NiaPreferencesDataSource,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val token = runBlocking { niaPreferencesDataSource.getAuthToken() }

        val requestWithHeaders = originalRequest.newBuilder()
            .apply {
                if (!token.isNullOrEmpty()) {
                    header("Authorization", "Bearer $token")
                }
            }
            .build()
        return chain.proceed(requestWithHeaders)
    }
}