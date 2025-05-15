package com.fajarxfce.core.network

import com.fajarxfce.core.exception.AuthorizationException
import com.fajarxfce.core.exception.BadRequestException
import com.fajarxfce.core.exception.NetworkException
import com.fajarxfce.core.exception.NotFoundException
import com.fajarxfce.core.exception.UnknownException
import com.fajarxfce.core.result.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okio.IOException
import retrofit2.HttpException

suspend fun <T : Any> safeApiCall(apiToBeCalled: suspend () -> T): Resource<T> {
    return withContext(Dispatchers.IO) {
        try {
            Resource.Success(apiToBeCalled())
        } catch (e: HttpException) {
            val message = Json.parseToJsonElement(
                e.response()?.errorBody()?.toString().orEmpty()
            ).jsonObject["message"]?.jsonPrimitive?.content.orEmpty().ifEmpty {
                "An unknown error occurred, please try again later."
            }
            when(e.code()) {
                400 -> Resource.Error(BadRequestException(message))
                401 -> Resource.Error(AuthorizationException(message))
                404 -> Resource.Error(NotFoundException(message))
                else -> Resource.Error(UnknownException(message))
            }
        } catch (e: IOException) {
            Resource.Error(NetworkException())
        } catch (e: Exception) {
            Resource.Error(UnknownException())
        }
    }
}