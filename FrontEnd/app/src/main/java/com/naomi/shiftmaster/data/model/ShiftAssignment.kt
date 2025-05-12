package com.naomi.shiftmaster.data.model

data class ShiftAssignment(
    val employeeId: String,
    val shiftType: String,  // e.g., "Morning", "Evening", "Night"
    val date: String,       // You might want to use a proper date type in a real app
    val isActive: Boolean = true,
    val id: String,
    val firstName: String,
    val lastName: String,
    val employeeRole: String,
    val shiftDate: String,
    val shiftTime: String

)
