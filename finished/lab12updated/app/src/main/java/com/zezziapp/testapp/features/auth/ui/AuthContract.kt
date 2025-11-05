package com.zezziapp.testapp.features.auth.ui

object AuthContract {

    sealed interface Intent {
        data class EmailChanged(val value: String) : Intent
        data class PasswordChanged(val value: String) : Intent
        data object SubmitLogin : Intent
        data object SubmitRegister : Intent
        data object ToggleMode : Intent      // login <-> register
    }

    data class State(
        val isLoading: Boolean = false,
        val isLoginMode: Boolean = true,    // true -> login, false -> register
        val email: String = "",
        val password: String = "",
        val error: String? = null,
        val isLoggedIn: Boolean = false
    )

    sealed interface Effect {
        data class ShowMessage(val text: String) : Effect
        data object NavigateToHome : Effect
    }
}