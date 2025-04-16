package com.fajarxfce.core.data.util

import com.fajarxfce.core.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class NetworkResource<T> {
    fun asFlow() : Flow<Result<T>> = flow {
        emit(Result.Loading)
        try {
            val apiResponse = createCall()
            emit(Result.Success(apiResponse))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    protected abstract suspend fun createCall(): T
}