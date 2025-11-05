package com.ai.agent.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, emailError = null, registerError = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, passwordError = null, registerError = null) }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = confirmPassword, confirmPasswordError = null, registerError = null) }
    }

    fun onRegister(onSuccess: () -> Unit) {
        val email = _uiState.value.email.trim()
        val password = _uiState.value.password
        val confirmPassword = _uiState.value.confirmPassword
        var valid = true
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.update { it.copy(emailError = "Invalid email") }
            valid = false
        }
        if (password.length < 6) {
            _uiState.update { it.copy(passwordError = "Password must be at least 6 characters") }
            valid = false
        }
        if (confirmPassword != password) {
            _uiState.update { it.copy(confirmPasswordError = "Passwords do not match") }
            valid = false
        }
        if (!valid) return
        _uiState.update { it.copy(isLoading = true, registerError = null) }
        CoroutineScope(Dispatchers.Main).launch {
            kotlinx.coroutines.delay(1000)
            if (email == "newuser@example.com") {
                _uiState.update { it.copy(isLoading = false, registerError = "Email already registered") }
            } else {
                _uiState.update { it.copy(isLoading = false) }
                onSuccess()
            }
        }
    }
}

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val registerError: String? = null,
    val isLoading: Boolean = false
)

