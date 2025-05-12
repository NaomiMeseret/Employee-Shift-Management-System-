package com.naomi.shiftmaster.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.naomi.shiftmaster.data.repository.EmployeeRepository
import com.naomi.shiftmaster.data.model.Employee
import com.naomi.shiftmaster.data.model.Shift
import com.naomi.shiftmaster.data.model.Attendance
import com.naomi.shiftmaster.data.model.ProfileUpdateRequest
import com.naomi.shiftmaster.data.model.ShiftUpdate

class EmployeeViewModel(
    private val repository: EmployeeRepository = EmployeeRepository()
) : ViewModel() {

    // State for all employees
    private val _employees = MutableStateFlow<List<Employee>>(emptyList())
    val employees: StateFlow<List<Employee>> = _employees.asStateFlow()

    // State for a single employee
    private val _currentEmployeeId = MutableStateFlow<Int?>(null)

    val currentEmployeeId: StateFlow<Int?> = _currentEmployeeId.asStateFlow()
    fun setCurrentEmployeeId(id: Int) {
        _currentEmployeeId.value = id
    }

    // State for a single employee
    private val _currentEmployeeRole = MutableStateFlow<String?>(null)

    val currentEmployeeRole: StateFlow<String?> = _currentEmployeeRole.asStateFlow()
    fun setCurrentEmployeeRole(role: String) {
        _currentEmployeeRole.value = role
    }

    // State for all assigned shifts
    private val _assignedShifts = MutableStateFlow<List<Shift>>(emptyList())
    val assignedShifts: StateFlow<List<Shift>> = _assignedShifts.asStateFlow()


    // Fetch all employees
    fun fetchEmployees() {
        viewModelScope.launch {
            val result = repository.getAllEmployees()
            _employees.value = result
        }
    }

    // Fetch single employee by id
    fun fetchEmployee(id: Int) {
        viewModelScope.launch {
            val result = repository.getEmployee(id)
            setCurrentEmployeeId(result.id)
        }
    }

    // Register new employee
    fun registerEmployee(employee: Employee) {
        viewModelScope.launch {
            repository.registerEmployee(employee)
        }
    }

    // Update employee by id
    fun updateEmployee(id: Int, employee: ProfileUpdateRequest) {
        viewModelScope.launch {
            repository.updateEmployee(id, employee)
        }
    }

    // Login
    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = repository.login(email, password)
            setCurrentEmployeeId(result.id)
            setCurrentEmployeeRole(if(result.isAdmin) "Admin" else "Employee")
            println("but here it says ${currentEmployeeId.value}")

            
        }
    }

    // Assign shift
    fun assignShift(shiftId: String, employeeId: Int, date: String, shiftType: String) {
        viewModelScope.launch {
            repository.assignShift(shiftId, employeeId, date, shiftType)
        }
    }
    fun updateShift(id: Int, shiftType: String, date: String){
        
        viewModelScope.launch{

            repository.updateShift(id.toString(), ShiftUpdate(shiftType =shiftType, date = date))
        }
            
    }
    fun loadAssignedShifts(id: Int){
        viewModelScope.launch {
            println("✅ here is the id ${id}")
            try {
                val shifts = repository.getAssignedShift(id)
                println("✅Got all shifts of ${id} ${shifts}")
                _assignedShifts.value = shifts

            } catch (e: Exception) {
                Log.e("EmployeeShiftVM", "Failed to fetch shifts", e)
                _assignedShifts.value = emptyList()
            }
        }
    }

    fun signOut() {
        _currentEmployeeId.value = null
        _currentEmployeeRole.value = null
        _assignedShifts.value = emptyList()
        _employees.value = emptyList()
    }

}