package com.naomi.shiftmaster.data.model

data class Attendance(
    val id: String = "", // This would typically be auto-generated
    val employeeId: String,
    val date: String,
    val clockInTime: String,
    val clockOutTime: String? = null,
    val status: String = "active", // enum = ["active", "on leave", "inactive"]
    val totalHours: Double = 0.0 // This would be calculated when clock out occurs
)
