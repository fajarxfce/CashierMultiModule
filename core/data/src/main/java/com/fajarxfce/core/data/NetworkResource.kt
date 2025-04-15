package com.fajarxfce.core.data

import com.fajarxfce.core.data.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkOnlyResource<ResultType, RequestType> {
    private val result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        fetchFromNetwork()
            .map { apiResponse ->

                when(apiResponse) {
                    is ApiResponse.Success -> {
                        val transformedData = transformResponse(apiResponse.data)
                        saveResponse(apiResponse.data)
                        emit(Resource.Success(transformedData))
                    }
                    is ApiResponse.Empty -> {
                        handleFetchFailure()
                    }
                    is ApiResponse.Error -> {
                        emit(Resource.Error(apiResponse.errorMessage))
                        handleFetchFailure()
                    }
                }
            }
    }

    protected abstract fun saveResponse(data: RequestType)

    protected abstract suspend fun transformResponse(data: RequestType): ResultType

    protected open fun handleFetchFailure() {}

    protected abstract suspend fun fetchFromNetwork(): Flow<ApiResponse<RequestType>>

    fun asFlow(): Flow<Resource<ResultType>> = result
}