package com.fajarxfce.core.data.network

import com.fajarxfce.core.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkBoundResource <ResultType, RequestType>{
    fun asFlow(): Flow<com.fajarxfce.core.result.Result<ResultType>> = flow {
        emit(Result.Loading)

        // First, load from database
        val dbSource = loadFromDb()
        if (shouldFetch(dbSource.firstOrNull())) {
            // Need fresh data, fetch from network
            emit(Result.Loading) // Optional: update UI that we're loading data
            try {
                val apiResponse = createCall()
                saveCallResult(apiResponse)
                // Re-emit from DB after save
                emitAll(loadFromDb().map { data ->
                    Result.Success(data)
                })
            } catch (e: Exception) {
                // If we have data in DB despite error, emit it
                val localData = loadFromDb().firstOrNull()
                if (localData != null) {
                    emitAll(loadFromDb().map { Result.Success(it) })
                } else {
                    emit(Result.Error(e))
                }
            }
        } else {
            // Don't need fresh data, use DB data
            emitAll(dbSource.map { Result.Success(it) })
        }
    }

    protected abstract fun loadFromDb(): Flow<ResultType>
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected abstract suspend fun createCall(): RequestType
    protected abstract suspend fun saveCallResult(data: RequestType)
}