package com.naomi.shiftmaster.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.naomi.shiftmaster.data.model.Employee
import com.naomi.shiftmaster.data.model.PasswordChangeRequest
import com.naomi.shiftmaster.data.model.ProfileUpdateRequest
import com.naomi.shiftmaster.data.repository.EmployeeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EmployeeProfileViewModel(
    private val  repository: EmployeeRepository = EmployeeRepository()
) : ViewModel() {

    private val _profileState = MutableStateFlow<Employee?>(null)
    val profileState: StateFlow<Employee?> = _profileState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()


    fun loadUserDetails(id: Int){
        viewModelScope.launch {
            println("✅ here is the id ${id}")
            try {
                val employee = repository.getEmployee(id)
                println("✅Got all shifts of ${id} ${employee}")
                _profileState.value = employee

            } catch (e: Exception) {
                Log.e("EmployeeShiftVM", "Failed to fetch shifts", e)
                _profileState.value = null
            }
        }
    }

    fun clearMessage() {
        _message.value = null
    }

    fun updateProfile(id: Int, employee: ProfileUpdateRequest) {

        viewModelScope.launch {
            _isLoading.value = true
            val success = repository.updateEmployee(id, employee).id == _profileState.value?.id
            _isLoading.value = false
            _message.value = if (success) "Profile updated successfully" else "Failed to update profile"
        }
    }

    fun changePassword(employeeId: Int, currentPassword: String, newPassword: String, confirmPassword: String) {

        if (confirmPassword != newPassword ){
            _message.value = "Correctly confirm password"
            return
        }


        val request = PasswordChangeRequest(
            currentPassword=currentPassword,
            newPassword = newPassword,

        )

        viewModelScope.launch {
            _isLoading.value = true
            val success = repository.changePassword(employeeId, request)
            _isLoading.value = false
            _message.value = if (success) "Password changed successfully" else "Failed to change password"
        }
    }

    fun signOut(onSignOut: () -> Unit) {
        viewModelScope.launch {
            onSignOut()
        }
    }
}