package com.naomi.shiftmaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naomi.shiftmaster.data.model.Attendance
import com.naomi.shiftmaster.data.model.Employee
import com.naomi.shiftmaster.data.repository.EmployeeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminAttendanceViewModel(
    private val repository: EmployeeRepository = EmployeeRepository()
) : ViewModel() {

    data class AttendanceRecord(
        val employee: Employee,
        val shiftType: String,
        val isPresent: Boolean
    )

    data class DailyAttendance(
        val date: String,
        val records: List<AttendanceRecord>
    )

    private val _groupedAttendance = MutableStateFlow<List<DailyAttendance>>(emptyList())
    val groupedAttendance: StateFlow<List<DailyAttendance>> = _groupedAttendance.asStateFlow()

    init {
        fetchGroupedAttendance()
    }

    private fun fetchGroupedAttendance() {
        viewModelScope.launch {
            val attendances = repository.getAllEmployeesWithAttendance()
            println("\uD83D\uDCCB Attendances fetched: ${attendances.size}")
            attendances.forEach { println("  ➤ $it") }

            val employees = repository.getAllEmployees()
            println("\uD83D\uDC65 Employees fetched: ${employees.size}")
            employees.forEach { println("  ➤ $it") }

            val grouped = attendances.groupBy { it.date }.map { (date, dailyList) ->
                println("\uD83D\uDCC6 Processing date: $date")
                val records = dailyList.mapNotNull { att ->
                    println("\uD83D\uDD0D Looking for employee with ID: ${att.employeeId}")
                    val emp = employees.find { it.id.toString() == att.employeeId }
                    println("   → Found: $emp")

                    emp?.let {
                        AttendanceRecord(
                            employee = it,
                            shiftType = att.status,
                            isPresent = att.clockInTime.isNotBlank()
                        )
                    }
                }
                println("✅ Records for $date: ${records.size}")
                DailyAttendance(date = date, records = records)
            }.sortedByDescending { it.date }

            println("\uD83D\uDCCA Grouped attendance ready: ${grouped.size} days")
            _groupedAttendance.value = grouped
        }
    }

    fun downloadAttendanceData() {
        println("Exporting attendance data: ${_groupedAttendance.value}")
    }

    fun signOut(onSignOut: () -> Unit) {
        viewModelScope.launch {
            // Optional: Clear local state or call backend logout if needed

            onSignOut()
        }
    }
} 
