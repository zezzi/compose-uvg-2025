package com.zezziapp.testapp.features.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zezziapp.testapp.core.util.Result
import com.zezziapp.testapp.features.auth.domain.usecase.LoginUser
import com.zezziapp.testapp.features.auth.domain.usecase.RegisterUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUser: LoginUser,
    private val registerUser: RegisterUser
) : ViewModel() {

    var state by mutableStateOf(AuthContract.State())
        private set

    private val _effects = MutableSharedFlow<AuthContract.Effect>()
    val effects = _effects.asSharedFlow()

    fun onIntent(intent: AuthContract.Intent) {
        when (intent) {
            is AuthContract.Intent.EmailChanged -> reduce {
                it.copy(email = intent.value, error = null)
            }
            is AuthContract.Intent.PasswordChanged -> reduce {
                it.copy(password = intent.value, error = null)
            }
            AuthContract.Intent.SubmitLogin -> login()
            AuthContract.Intent.SubmitRegister -> register()
            AuthContract.Intent.ToggleMode -> reduce {
                it.copy(
                    isLoginMode = !it.isLoginMode,
                    error = null
                )
            }
        }
    }

    private fun login() {
        val email = state.email.trim()
        val password = state.password

        if (email.isBlank() || password.length < 6) {
            reduce { it.copy(error = "Invalid credentials") }
            return
        }

        reduce { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            when (val res = loginUser(email, password)) {
                is Result.Ok -> {
                    reduce { it.copy(isLoading = false, isLoggedIn = true) }
                    emit(AuthContract.Effect.NavigateToHome)
                }
                is Result.Err -> {
                    reduce {
                        it.copy(
                            isLoading = false,
                            error = res.throwable.message ?: "Login failed"
                        )
                    }
                    emit(AuthContract.Effect.ShowMessage("Login failed"))
                }
            }
        }
    }

    private fun register() {
        val email = state.email.trim()
        val password = state.password

        if (email.isBlank() || password.length < 6) {
            reduce { it.copy(error = "Password must be at least 6 characters") }
            return
        }

        reduce { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            when (val res = registerUser(email, password)) {
                is Result.Ok -> {
                    reduce { it.copy(isLoading = false, isLoggedIn = true) }
                    emit(AuthContract.Effect.NavigateToHome)
                }
                is Result.Err -> {
                    reduce {
                        it.copy(
                            isLoading = false,
                            error = res.throwable.message ?: "Registration failed"
                        )
                    }
                    emit(AuthContract.Effect.ShowMessage("Registration failed"))
                }
            }
        }
    }

    private inline fun reduce(block: (AuthContract.State) -> AuthContract.State) {
        state = block(state)
    }

    private fun emit(effect: AuthContract.Effect) {
        viewModelScope.launch { _effects.emit(effect) }
    }
}