package com.fajarxfce.core.model.data.auth.request

import com.fajarxfce.core.model.data.auth.User

data class LoginRequest(
    val email: String,
    val password: String,
) {
    companion object {
        const val USERNAME_MIN_LENGTH = 3
        const val PASSWORD_MIN_LENGTH = 6
    }
}