package com.example.shiftmaster.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.shiftmaster.data.model.Shift

class EmployeeShiftViewModel : ViewModel() {
    // mock data; later replace with real repository/API
    val assignedShifts = mutableStateOf(
        listOf(
            Shift("1", "A1", "4/5/2025", "Morning"),
            Shift("2", "A1", "4/4/2025", "Evening"),
            Shift("3", "A1", "4/3/2025", "Night")
        )
    )
}
