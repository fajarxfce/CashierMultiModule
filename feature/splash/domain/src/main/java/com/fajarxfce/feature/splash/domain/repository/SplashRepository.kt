package com.fajarxfce.feature.splash.domain.repository

import com.fajarxfce.core.result.Resource

interface SplashRepository {
    suspend fun checkUserLoggedIn(): Resource<Unit>
}