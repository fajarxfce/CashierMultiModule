package com.fajarxfce.core.data

import com.fajarxfce.core.data.domain.repository.IAuthRepository
import com.fajarxfce.core.model.data.auth.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(

) : IAuthRepository {
    override fun login(
        username: String,
        password: String,
    ): Flow<User> {
        return flow { emit(User("888", "ANJAY", "anjay@anjay.com", "anjay")) }
    }

}