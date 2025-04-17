package com.fajarxfce.core.network

import android.util.Log
import com.fajarxfce.core.AuthEvent
import com.fajarxfce.core.AuthEventBus
import com.fajarxfce.core.datastore.NiaPreferencesDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnauthorizedInterceptor @Inject constructor(
    private val niaPreferencesDataSource: NiaPreferencesDataSource,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code == 401) {
            CoroutineScope(Dispatchers.IO).launch {
                niaPreferencesDataSource.clearAuthToken()
                AuthEventBus.sendEvent(AuthEvent.Logout) // Send the logout event
            }
        }

        return response
    }
}