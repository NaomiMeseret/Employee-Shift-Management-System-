package com.example.shiftmaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    // Hold email and password inputs
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    // Hold login result: success or error
    private val _loginResult = MutableStateFlow<LoginResult?>(null)
    val loginResult: StateFlow<LoginResult?> = _loginResult

    // Fake user list with unique emails and roles
    private val users = listOf(
        User("admin@example.com", "admin123", "admin"),
        User("employee1@example.com", "emp123", "employee"),
        User("employee2@example.com", "emp456", "employee")
    )

    // Called when email text changes
    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    // Called when password text changes
    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    // Simulate login process
    fun login() {
        viewModelScope.launch {
            delay(800) // simulate network delay

            val currentEmail = _email.value
            val currentPassword = _password.value

            // Find a matching user
            val user = users.find { it.email == currentEmail && it.password == currentPassword }

            if (user != null) {
                // Success: return role
                _loginResult.value = LoginResult.Success(user.role)
            } else {
                // Error: invalid credentials
                _loginResult.value = LoginResult.Error("Invalid email or password")
            }
        }
    }

    // Simple data class for users
    data class User(val email: String, val password: String, val role: String)

    // Sealed class for login result
    sealed class LoginResult {
        data class Success(val role: String) : LoginResult()
        data class Error(val message: String) : LoginResult()
    }
}
