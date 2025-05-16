package com.fajarxfce.feature.login.data.model

import com.fajarxfce.core.model.data.auth.User
import com.google.gson.annotations.SerializedName

internal data class LoginResponse(

    @field:SerializedName("token_type")
    val tokenType: String? = null,

    @field:SerializedName("token")
    val token: String?
)