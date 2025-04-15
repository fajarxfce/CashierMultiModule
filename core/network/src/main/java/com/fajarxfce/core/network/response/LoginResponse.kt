package com.fajarxfce.core.network.response

import com.fajarxfce.core.model.data.auth.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("token_type")
	val tokenType: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)

fun LoginResponse.toUser(): User {
    return User(
        token = data?.token
    )
}