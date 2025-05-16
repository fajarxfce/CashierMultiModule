package com.fajarxfce.feature.splash.domain.usecase

import com.fajarxfce.feature.splash.domain.repository.SplashRepository
import javax.inject.Inject

class CheckUserLoggedInUseCase @Inject constructor(
    private val splashRepository: SplashRepository
){
    suspend operator fun invoke() = splashRepository.CheckUserLoggedIn()
}