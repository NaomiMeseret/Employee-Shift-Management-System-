package com.naomi.shiftmaster.data.model


data class Employee(
    val name: String,
    val email: String,
    val id: Int,
    val password: String,
    val profilePicture: String = "default.jpg",
    val phone: String,
    val position: String,
    val status: String = "inactive", // enum ["active", "inactive", "on leave"]
    val isAdmin: Boolean = false
)