package com.example.shiftmaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shiftmaster.data.model.Employee
import com.example.shiftmaster.data.model.PasswordChangeRequest
import com.example.shiftmaster.data.model.ProfileUpdateRequest
import com.example.shiftmaster.data.repository.EmployeeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EmployeeProfileViewModel(
    private val repository: EmployeeRepository = EmployeeRepository()
) : ViewModel() {

    // UI state for the profile
    private val _profileState = MutableStateFlow<Employee?>(null)
    val profileState: StateFlow<Employee?> = _profileState.asStateFlow()

    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Success/error message
    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()

    init {
        // Load the employee profile when the ViewModel is created
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.currentEmployee.collectLatest { employee ->
                _profileState.value = employee
                _isLoading.value = false
            }
        }
    }

    fun updateProfile(fullName: String, phoneNumber: String, bio: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val request = ProfileUpdateRequest(fullName, phoneNumber, bio)
            val success = repository.updateProfile(request)

            if (success) {
                _message.value = "Profile updated successfully"
            } else {
                _message.value = "Failed to update profile"
            }
            _isLoading.value = false
        }
    }

    fun changePassword(currentPassword: String, newPassword: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val request = PasswordChangeRequest(currentPassword, newPassword)
            val success = repository.changePassword(request)

            if (success) {
                _message.value = "Password changed successfully"
            } else {
                _message.value = "Failed to change password"
            }
            _isLoading.value = false
        }
    }

    fun updateProfilePhoto(photoUrl: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val success = repository.updateProfilePhoto(photoUrl)

            if (success) {
                _message.value = "Profile photo updated"
            } else {
                _message.value = "Failed to update profile photo"
            }
            _isLoading.value = false
        }
    }

    // Clear any messages
    fun clearMessage() {
        _message.value = null
    }

    // Sign out function
    fun signOut() {
        // In a real app, you would clear the user session and navigate to the login screen
    }
}