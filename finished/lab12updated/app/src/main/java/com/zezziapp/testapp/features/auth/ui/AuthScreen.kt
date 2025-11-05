package com.zezziapp.testapp.features.auth.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AuthScreen(
    vm: AuthViewModel = hiltViewModel(),
    onAuthenticated: () -> Unit
) {
    val state = vm.state
    val ctx = LocalContext.current

    LaunchedEffect(Unit) {
        vm.effects.collect { eff ->
            when (eff) {
                is AuthContract.Effect.NavigateToHome -> onAuthenticated()
                is AuthContract.Effect.ShowMessage ->
                    Toast.makeText(ctx, eff.text, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp)
        ) {
            Column(
                Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    if (state.isLoginMode) "Login" else "Register",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.email,
                    onValueChange = { vm.onIntent(AuthContract.Intent.EmailChanged(it)) },
                    label = { Text("Email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = state.password,
                    onValueChange = { vm.onIntent(AuthContract.Intent.PasswordChanged(it)) },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                if (state.error != null) {
                    Spacer(Modifier.height(8.dp))
                    Text(state.error!!, color = MaterialTheme.colorScheme.error)
                }

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (state.isLoginMode) {
                            vm.onIntent(AuthContract.Intent.SubmitLogin)
                        } else {
                            vm.onIntent(AuthContract.Intent.SubmitRegister)
                        }
                    },
                    enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(if (state.isLoginMode) "Login" else "Register")
                    }
                }

                TextButton(
                    onClick = { vm.onIntent(AuthContract.Intent.ToggleMode) },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        if (state.isLoginMode)
                            "Don't have an account? Register"
                        else
                            "Already have an account? Login"
                    )
                }
            }
        }
    }
}