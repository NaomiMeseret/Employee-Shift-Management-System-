package com.naomi.shiftmaster.data.model

data class ProfileUpdateRequest(
    val name: String? = null,
    val profilePicture: String? = "default.jpg",
    val phone: String?= null,
    val position: String? = null,
    val status: String? = null, // enum ["active", "inactive", "on leave"]
    val isAdmin: Boolean? = null,

)