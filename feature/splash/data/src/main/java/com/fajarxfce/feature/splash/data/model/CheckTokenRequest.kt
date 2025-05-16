package com.fajarxfce.feature.splash.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CheckTokenRequest(
    val token: String
)
