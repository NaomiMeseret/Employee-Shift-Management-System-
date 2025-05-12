package com.naomi.shiftmaster.data.model
import com.naomi.shiftmaster.data.model.Employee
data class LoginResponse(
    val message: String,
    val employee: Employee
)
