package com.naomi.shiftmaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naomi.shiftmaster.data.model.Attendance
import com.naomi.shiftmaster.data.model.Employee
import com.naomi.shiftmaster.data.model.ShiftWithEmployee
import com.naomi.shiftmaster.data.repository.EmployeeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminAttendanceViewModel(
    private val repository: EmployeeRepository = EmployeeRepository()
) : ViewModel() {


    private val _attendances = MutableStateFlow<List<Attendance>>(emptyList())
    val  attendances : StateFlow<List<Attendance>> = _attendances.asStateFlow()

    private val _shiftWithEmployees = MutableStateFlow<List<ShiftWithEmployee>>(emptyList())
    val shiftWithEmployees: StateFlow<List<ShiftWithEmployee>> = _shiftWithEmployees.asStateFlow()


    init {
        viewModelScope.launch {
            try {
                val fetchedEmployees = repository.getAllEmployees()
                val fetchedShifts = repository.getAllAssignedShifts()

                println("No it is shift fetching ${fetchedShifts}")
                val shiftList = mutableListOf<ShiftWithEmployee>()

                for (shift in fetchedShifts) {
                    val employee = fetchedEmployees.find { it.id.toString() == shift.employeeId }
                    println("why is shift id null ${shift.id}")
                    if (employee != null) {
                        shiftList.add(
                            ShiftWithEmployee(
                                id = shift.id,
                                employee = employee,
                                date = shift.date,
                                shiftType = shift.shiftType,
                                attendance= shift.attendance
                            )
                        )
                    }
                    println("added: ${shift}")
                }
                _shiftWithEmployees.value = shiftList

            } catch (e: Exception) {
                println("❌ Error fetching data: ${e.message}")
            }
        }
    }

    fun downloadAttendanceData() {
        println("Exporting attendance data: ${_shiftWithEmployees.value}")
    }

    fun signOut(onSignOut: () -> Unit) {
        viewModelScope.launch {
            // Optional: Clear local state or call backend logout if needed

            onSignOut()
        }
    }
} 