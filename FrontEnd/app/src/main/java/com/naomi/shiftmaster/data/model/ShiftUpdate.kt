package com.naomi.shiftmaster.data.model

data class ShiftUpdate(
    val employeeId: String? = null,
    val date: String? = null,
    val shiftType: String? = null // "Morning", "Evening", or "Night"
)