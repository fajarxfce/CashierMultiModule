package com.fajarxfce.core

import com.fajarxfce.core.result.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T : Any> safeApiCall(apiToBeCalled: suspend () -> T): Unit {
    return withContext(Dispatchers.IO) {
        try {
            Resource.Success(apiToBeCalled())
        } catch (e: Exception) {

        }
    }
}