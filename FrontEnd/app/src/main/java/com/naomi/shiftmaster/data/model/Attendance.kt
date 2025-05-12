
package com.naomi.shiftmaster.data.model

import java.util.Date

data class Attendance(
    val date: String,
    val actionType: String,
    val status: String = "active", // enum = ["active", "on leave", "inactive"]
    val time: String // This would be calculated when clock out occurs
)
