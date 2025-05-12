package com.naomi.shiftmaster.data.model

data class Shift(
    val id: String,
    val employeeId: String,
    val date: String,
    val shiftType: String // "Morning", "Evening", or "Night"
)
