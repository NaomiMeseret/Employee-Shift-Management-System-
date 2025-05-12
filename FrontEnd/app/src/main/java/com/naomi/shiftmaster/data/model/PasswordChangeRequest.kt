package com.naomi.shiftmaster.data.model

data class PasswordChangeRequest(
    val currentPassword: String,
    val newPassword: String
)

