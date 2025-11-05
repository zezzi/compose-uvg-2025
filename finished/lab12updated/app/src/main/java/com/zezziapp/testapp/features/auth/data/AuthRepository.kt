package com.zezziapp.testapp.features.auth.data
import com.zezziapp.testapp.core.util.Result

interface AuthRepository {
    suspend fun register(email: String, password: String): Result<Unit>
    suspend fun login(email: String, password: String): Result<Unit>
    fun currentUserId(): String?
    fun logout()
}