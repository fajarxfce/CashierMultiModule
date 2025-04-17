package com.fajarxfce.core.exception

class UnauthorizedException(
    message: String = "Unauthorized",
    cause: Throwable? = null,
) : Exception(message, cause) {

    override val message: String
        get() = super.message ?: "Unauthorized"

    companion object {
        const val ERROR_CODE = 401
    }
}
