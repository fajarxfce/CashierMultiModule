package com.fajarxfce.core.network

import com.fajarxfce.core.exception.UnauthorizedException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnauthorizedInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (response.code == 401) {
            throw UnauthorizedException("Unauthorized: Token may be expired")
        }

        return response
    }
}