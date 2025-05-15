package com.fajarxfce.core.result

import com.fajarxfce.core.exception.BaseException

sealed class Resource<out T> {
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Error(val exception: BaseException) : Resource<Nothing>()
}

inline fun <T> Resource<T>.onSuccess(action: (T) -> Unit): Resource<T>{
    if (this is Resource.Success) action(data)
    return this
}

inline fun <T> Resource<T>.onFailure(action: (BaseException) -> Unit): Resource<T> {
    if (this is Resource.Error) action(exception)
    return this
}

inline fun <T> Resource<T>.fold(
    onSuccess: (T) -> Unit,
    onFailure: (BaseException) -> Unit
) {
    when (this) {
        is Resource.Success -> onSuccess(data)
        is Resource.Error -> onFailure(exception)
    }
}

fun <T> Resource<T>.toUnit(): Resource<Unit> {
    return when (this) {
        is Resource.Success -> Resource.Success(Unit)
        is Resource.Error -> Resource.Error(exception)
    }
}