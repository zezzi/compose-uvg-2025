package com.zezziapp.testapp.features.auth.domain.usecase

import com.zezziapp.testapp.core.util.Result
import com.zezziapp.testapp.features.auth.data.AuthRepository

class LoginUser(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> =
        repo.login(email, password)
}