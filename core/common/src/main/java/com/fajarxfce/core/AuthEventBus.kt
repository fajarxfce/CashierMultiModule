package com.fajarxfce.core

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthEventBus @Inject constructor() {
    private val _events = MutableSharedFlow<AuthEvent>()
    val events: SharedFlow<AuthEvent> = _events.asSharedFlow()

    suspend fun emitEvent(event: AuthEvent) {
        _events.emit(event)
    }

    sealed class AuthEvent {
        data object Unauthorized : AuthEvent()
    }
}