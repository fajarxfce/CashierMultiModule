package com.fajarxfce.core.data.util

import com.fajarxfce.core.AuthEventBus
import com.fajarxfce.core.exception.UnauthorizedException
import com.fajarxfce.core.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber


abstract class NetworkResource<T> {
    fun asFlow() : Flow<Result<T>> = flow {
        emit(Result.Loading)
        try {
            val apiResponse = createCall()
            saveCallResult(apiResponse)
            emit(Result.Success(apiResponse))
        } catch (e: UnauthorizedException) {
            emit(Result.Error(e))
        }catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    protected abstract suspend fun saveCallResult(data: T)

    protected abstract suspend fun createCall(): T
}