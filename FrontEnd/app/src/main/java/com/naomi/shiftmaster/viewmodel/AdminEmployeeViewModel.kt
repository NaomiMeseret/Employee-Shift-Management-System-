package com.naomi.shiftmaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naomi.shiftmaster.data.model.Employee
import com.naomi.shiftmaster.data.model.ProfileUpdateRequest
import com.naomi.shiftmaster.data.repository.EmployeeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminEmployeeViewModel(
    private val repository: EmployeeRepository = EmployeeRepository()
) : ViewModel() {

    private val _employees = MutableStateFlow<List<Employee>>(emptyList())
    val employees: StateFlow<List<Employee>> = _employees.asStateFlow()

    init {
        fetchEmployees()
    }

    fun fetchEmployees() {
        viewModelScope.launch {
            val result = repository.getAllEmployees()
            _employees.value = result
        }
    }

    fun deleteEmployee(id: Int, onDeleteClick: (String) -> Unit) {
        viewModelScope.launch {
            repository.deleteEmployee(id)
            fetchEmployees()
            onDeleteClick(id.toString())
        }
    }
}
