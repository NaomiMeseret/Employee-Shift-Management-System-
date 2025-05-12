package com.naomi.shiftmaster.data.model

data class AdminShift(
    val id: String,
    val employeeId: String,
    val shiftType: String,  // "Morning", "Evening", "Night"
    val date: String
)

// This class represents an employee with their assigned shift
// for display in the admin shift management screen
data class EmployeeShift(
    val id: String,
    val name: String,
    val role: String,
    val shift: String
)