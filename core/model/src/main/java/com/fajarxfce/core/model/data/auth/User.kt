package com.fajarxfce.core.model.data.auth

data class User(
    val id: String,
    val name: String,
    val email: String,
    val token: String
) {
    companion object {
        val Empty = User("", "", "", "")
    }
}