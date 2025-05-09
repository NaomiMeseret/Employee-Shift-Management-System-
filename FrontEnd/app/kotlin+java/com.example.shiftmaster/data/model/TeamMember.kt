package com.example.shiftmaster.data.model

data class TeamMember(
    val id: String,
    val name: String,
    val role: String,
    val status: String // "Active" or "On leave"
)
