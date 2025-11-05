package com.zezziapp.testapp.features.auth.domain.usecase

import com.zezziapp.testapp.features.auth.data.AuthRepository

class LogoutUser(
    private val repo: AuthRepository
) {
    operator fun invoke() = repo.logout()
}
