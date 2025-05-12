package com.naomi.shiftmaster.data.model
import com.naomi.shiftmaster.data.model.Employee
data class ShiftWithEmployee(
    val id: String,
    val employee: Employee,
    val date: String,
    val shiftType: String, // "Morning", "Evening", or "Night"
    val attendance: List<Attendance>? = listOf()
)