package com.fajarxfce.core.network.model

import android.annotation.SuppressLint
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
open class BaseResponse<T>(
    val data: T? = null,
    val code: Int? = null,
    val message: String? = null,
)